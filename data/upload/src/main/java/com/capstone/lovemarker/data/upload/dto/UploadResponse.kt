package com.capstone.lovemarker.data.upload.dto

import com.capstone.lovemarker.domain.upload.entity.UploadResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse(
    @SerialName("memoryId")
    val memoryId: Int
) {
    fun toDomain() = UploadResponseEntity(
        memoryId = memoryId
    )
}
