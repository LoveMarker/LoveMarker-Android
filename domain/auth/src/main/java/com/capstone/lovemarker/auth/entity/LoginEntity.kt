package com.capstone.lovemarker.auth.entity

data class LoginEntity(
    val accessToken: String,
    val refreshToken: String,
    val isRegistered: Boolean
)