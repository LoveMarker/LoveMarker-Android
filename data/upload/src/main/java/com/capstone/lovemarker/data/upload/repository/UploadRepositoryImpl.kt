package com.capstone.lovemarker.data.upload.repository

import android.content.Context
import androidx.core.net.toUri
import com.capstone.lovemarker.core.common.util.ContentUriToRequestBody
import com.capstone.lovemarker.data.upload.dto.toData
import com.capstone.lovemarker.data.upload.source.UploadDataSource
import com.capstone.lovemarker.domain.upload.entity.UploadRequestEntity
import com.capstone.lovemarker.domain.upload.entity.UploadResponseEntity
import com.capstone.lovemarker.domain.upload.repository.UploadRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val uploadDataSource: UploadDataSource
) : UploadRepository {
    override suspend fun postMemory(
        images: List<String>,
        requestEntity: UploadRequestEntity
    ): Result<UploadResponseEntity> = runCatching {
        uploadDataSource.postMemory(
            files = createFormData(images),
            otherMap = requestEntity.toData()
        ).data.toDomain()
    }

    private fun createFormData(images: List<String>): List<MultipartBody.Part> =
        images.map { imageUrl ->
            ContentUriToRequestBody(
                context = context,
                contentUri = imageUrl.toUri()
            ).toFormData()
        }
}
