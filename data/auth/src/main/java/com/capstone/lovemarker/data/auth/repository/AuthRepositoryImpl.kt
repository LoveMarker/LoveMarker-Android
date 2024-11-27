package com.capstone.lovemarker.data.auth.repository

import com.capstone.lovemarker.core.datastore.source.user.UserDataStore
import com.capstone.lovemarker.data.auth.dto.request.LoginRequest
import com.capstone.lovemarker.domain.auth.entity.LoginEntity
import com.capstone.lovemarker.domain.auth.repository.AuthRepository
import com.capstone.lovemarker.data.auth.source.AuthDataSource
import com.capstone.lovemarker.domain.auth.entity.Token
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val userDataStore: UserDataStore
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

    override suspend fun saveTokens(token: Token) {
        userDataStore.apply {
            updateAccessToken(token.accessToken)
            updateRefreshToken(token.refreshToken)
        }
    }

    override suspend fun updateAutoLogin(configured: Boolean) {
        userDataStore.updateAutoLogin(configured = configured)
    }

    override suspend fun isAutoLoginEnabled() =
        userDataStore.user.first().autoLoginConfigured
}
