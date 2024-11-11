package com.capstone.lovemarker.domain.auth.entity

data class Token(
    val accessToken: String,
    val refreshToken: String
)
