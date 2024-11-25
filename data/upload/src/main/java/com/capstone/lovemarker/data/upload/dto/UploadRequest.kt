package com.capstone.lovemarker.data.upload.dto

import com.capstone.lovemarker.domain.upload.entity.UploadRequestEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

// todo: Double 타입이 제대로 전달되는지 확인
fun UploadRequestEntity.toData(): HashMap<String, RequestBody> = HashMap(
    this::class.members.associate {
        it.name to it.toString().toRequestBody("text/plain".toMediaTypeOrNull())
    }
)

//fun UploadRequestEntity.toData(): HashMap<String, RequestBody> = hashMapOf(
//    "latitude" to latitude.toPlainTextRequestBody(),
//    "longitude" to longitude.toPlainTextRequestBody(),
//    "address" to address.toPlainTextRequestBody(),
//    "date" to date.toPlainTextRequestBody(),
//    "title" to title.toPlainTextRequestBody(),
//    "content" to content.toPlainTextRequestBody()
//)
