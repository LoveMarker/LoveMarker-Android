package com.capstone.lovemarker.auth.source

import com.capstone.lovemarker.auth.dto.request.LoginRequest
import com.capstone.lovemarker.auth.dto.response.LoginResponse
import com.capstone.lovemarker.auth.service.AuthService
import com.capstone.lovemarker.core.network.model.BaseResponse
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {
    override suspend fun postLogin(
        socialToken: String,
        requestBody: LoginRequest,
    ): BaseResponse<LoginResponse> = authService.postLogin(
        socialToken = socialToken,
        requestBody = requestBody
    )
}
