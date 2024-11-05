package com.capstone.lovemarker.auth.entity

data class Token(
    val accessToken: String,
    val refreshToken: String
)
