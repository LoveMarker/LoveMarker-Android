package com.capstone.lovemarker.data.nickname.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NicknameRequest(
    @SerialName("nickname")
    val nickname: String
)
