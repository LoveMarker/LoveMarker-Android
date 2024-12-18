package com.capstone.lovemarker.data.auth.dto.response

import com.capstone.lovemarker.domain.auth.entity.LoginEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("type")
    val type: String, // Login or SignUp
) {
    fun toDomain() = LoginEntity(
        accessToken = accessToken,
        refreshToken = refreshToken,
        isRegistered = type == "Login"
    )
}
