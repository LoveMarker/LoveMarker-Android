package com.capstone.lovemarker.core.datastore.model

data class User(
    val accessToken: String,
    val refreshToken: String,
    val autoLoginConfigured: Boolean,
    val nickname: String,
)
