package com.capstone.lovemarker.data.nickname.dto

import com.capstone.lovemarker.domain.nickname.entity.NicknameEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NicknameResponse(
    @SerialName("status")
    val code: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
) {
    fun toDomain() = NicknameEntity(
        code = code,
        success = success,
        message = message
    )
}
