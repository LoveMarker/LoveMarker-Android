package com.capstone.lovemarker.core.common.util

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import timber.log.Timber
import java.io.ByteArrayOutputStream

class ContentUriToRequestBody(
    context: Context,
    private val contentUri: Uri
) : RequestBody() {
    private val contentResolver = context.contentResolver
    private var fileSize = -1L
    private var fileName = ""
    private var isCompressed = false
    private var compressedImage: ByteArray? = null

    init {
        queryFileInfoFromContentResolver()
        compressImage()
    }

    private fun queryFileInfoFromContentResolver() {
        contentResolver.query(
            contentUri,
            arrayOf(MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                initFileInfo(cursor)
            }
        }
    }

    private fun initFileInfo(cursor: Cursor) {
        fileSize = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
        fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
    }

    private fun compressImage() {
        val inputStream = contentResolver.openInputStream(contentUri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        // 필요한 경우 회전 각도를 조정한다.
        val rotatedBitmap = rotateImageIfRequired(originalBitmap)

        val outputStream = ByteArrayOutputStream()
        val imageMbSize = fileSize.toMbSize()
        Timber.d("BEFORE IMAGE SIZE: $imageMbSize")

        // 최대 크기를 넘는 경우에만 압축한다.
        if (imageMbSize >= MAX_MB_SIZE) {
            isCompressed = true

            outputStream.use { byteArrayOutputStream ->
                rotatedBitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    calcCompressQuality(imageMbSize),
                    byteArrayOutputStream
                )
            }

            saveCompressedImage(outputStream.toByteArray())
            saveCompressedImageSize(outputStream.toByteArray())
        }
    }

    private fun rotateImageIfRequired(originalBitmap: Bitmap): Bitmap {
        val inputStream = contentResolver.openInputStream(contentUri)

        inputStream?.use {
            val exif = ExifInterface(inputStream)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90F)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180F)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(270F)
                else -> return originalBitmap
            }

            return Bitmap.createBitmap(
                originalBitmap,
                0,
                0,
                originalBitmap.width,
                originalBitmap.height,
                matrix,
                true
            )
        }

        return originalBitmap
    }

    /**
     * 5MB 크기 넘지 않도록 하는 압축 품질 계산
     * imageMbSize > MAX_MB_SIZE: 품질이 100 미만으로 낮아짐.
     * imageMbSize ≤ MAX_MB_SIZE: 품질이 100 (최고 품질)
     * */
    private fun calcCompressQuality(imageMbSize: Double) =
        ((MAX_MB_SIZE / imageMbSize) * 100).toInt()

    private fun saveCompressedImage(outputByteArray: ByteArray) {
        compressedImage = outputByteArray
    }

    private fun saveCompressedImageSize(byteArray: ByteArray) {
        fileSize = byteArray.size.toLong()
        Timber.d("AFTER IMAGE SIZE: ${fileSize.toMbSize()}")
    }

    override fun contentLength(): Long = fileSize

    override fun contentType(): MediaType? =
        contentResolver.getType(contentUri)?.toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        if (isCompressed) {
            compressedImage?.let(sink::write)
            return
        }

        contentResolver.openInputStream(contentUri).use { inputStream ->
            inputStream?.source()?.let(sink::writeAll)
        }
    }

    private fun Long.toMbSize() = this / (KiB * KiB).toDouble()

    fun toFormData() = MultipartBody.Part.createFormData(KEY_NAME, fileName, this)

    companion object {
        private const val KEY_NAME = "images"
        private const val KiB = 1024
        private const val MAX_MB_SIZE = 5
    }
}
