package com.capstone.lovemarker.auth.repository

import com.capstone.lovemarker.auth.entity.LoginEntity

interface AuthRepository {
    suspend fun postLogin(
        socialToken: String,
        provider: String
    ): Result<LoginEntity>
}
