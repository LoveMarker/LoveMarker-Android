package com.capstone.lovemarker.core.datastore.model

data class UserData(
    val accessToken: String,
    val refreshToken: String,
    val autoLoginConfigured: Boolean
)
