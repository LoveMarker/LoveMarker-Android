package com.capstone.lovemarker.auth.repository

import com.capstone.lovemarker.auth.dto.request.LoginRequest
import com.capstone.lovemarker.auth.entity.LoginEntity
import com.capstone.lovemarker.auth.source.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun postLogin(
        socialToken: String,
        provider: String,
    ): Result<LoginEntity> = runCatching {
        authDataSource.postLogin(
            socialToken = socialToken,
            requestBody = LoginRequest(provider)
        ).data.toDomain()
    }
}
