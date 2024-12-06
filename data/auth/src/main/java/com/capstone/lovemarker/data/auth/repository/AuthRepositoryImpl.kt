package com.capstone.lovemarker.data.auth.repository

import com.capstone.lovemarker.data.auth.dto.request.LoginRequest
import com.capstone.lovemarker.data.auth.source.AuthDataSource
import com.capstone.lovemarker.domain.auth.entity.LoginEntity
import com.capstone.lovemarker.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun postLogin(
        socialToken: String,
        provider: String,
    ): Result<LoginEntity> = runCatching {
        authDataSource.postLogin(
            socialToken = socialToken,
            loginRequest = LoginRequest(provider)
        ).data.toDomain()
    }
}
