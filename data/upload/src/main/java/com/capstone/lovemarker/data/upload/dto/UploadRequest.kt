package com.capstone.lovemarker.data.upload.dto

import com.capstone.lovemarker.domain.upload.entity.UploadRequestEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun UploadRequestEntity.toData(): HashMap<String, RequestBody> = hashMapOf(
    "latitude" to latitude.toPlainTextRequestBody(),
    "longitude" to longitude.toPlainTextRequestBody(),
    "address" to address.toPlainTextRequestBody(),
    "date" to date.toPlainTextRequestBody(),
    "title" to title.toPlainTextRequestBody(),
    "content" to content.toPlainTextRequestBody()
)

fun <T> T.toPlainTextRequestBody() = this.toString().toRequestBody("text/plain".toMediaTypeOrNull())
