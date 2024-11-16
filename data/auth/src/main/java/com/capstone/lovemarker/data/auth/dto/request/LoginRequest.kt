package com.capstone.lovemarker.data.auth.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("token")
    val token: String,
    @SerialName("provider")
    val provider: String
)
