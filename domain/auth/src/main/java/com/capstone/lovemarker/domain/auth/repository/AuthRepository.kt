package com.capstone.lovemarker.domain.auth.repository

import com.capstone.lovemarker.domain.auth.entity.LoginEntity

interface AuthRepository {
    suspend fun postLogin(
        socialToken: String,
        provider: String
    ): Result<LoginEntity>
}
