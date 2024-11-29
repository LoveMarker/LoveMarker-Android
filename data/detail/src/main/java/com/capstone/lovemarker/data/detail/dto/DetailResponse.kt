package com.capstone.lovemarker.data.detail.dto

import com.capstone.lovemarker.domain.detail.entity.DetailEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailResponse(
    @SerialName("memoryId")
    val memoryId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("writer")
    val writer: String,
    @SerialName("address")
    val address: String,
    @SerialName("date")
    val date: String,
    @SerialName("content")
    val content: String,
    @SerialName("images")
    val images: List<String>,
    @SerialName("isWriter")
    val isWriter: Boolean,
) {
    fun toDomain() = DetailEntity(
        title = title,
        writer = writer,
        address = address,
        date = date,
        content = content,
        images = images
    )
}
