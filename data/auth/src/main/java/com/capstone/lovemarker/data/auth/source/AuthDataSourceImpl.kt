package com.capstone.lovemarker.data.auth.source

import com.capstone.lovemarker.data.auth.dto.request.LoginRequest
import com.capstone.lovemarker.data.auth.dto.response.LoginResponse
import com.capstone.lovemarker.data.auth.service.AuthService
import com.capstone.lovemarker.core.network.model.BaseResponse
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {
    override suspend fun postLogin(
        socialToken: String,
        loginRequest: LoginRequest
    ): BaseResponse<LoginResponse> =
        authService.postLogin(
            socialToken = socialToken,
            loginRequest = loginRequest
        )
}
