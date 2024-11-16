package com.capstone.lovemarker.domain.auth.repository

import com.capstone.lovemarker.domain.auth.entity.LoginEntity
import com.capstone.lovemarker.domain.auth.entity.Token

interface AuthRepository {
    suspend fun postLogin(
        socialToken: String,
        provider: String
    ): Result<LoginEntity>

    suspend fun saveTokens(token: Token)
    suspend fun updateAutoLogin(configured: Boolean)
    suspend fun isAutoLoginEnabled(): Boolean
}
