package com.capstone.lovemarker.data.nickname.dto

import com.capstone.lovemarker.domain.nickname.entity.NicknameEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NicknameResponse(
    @SerialName("nickname")
    val nickname: String
) {
    fun toDomain() = NicknameEntity(
        nickname = nickname
    )
}