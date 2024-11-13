package com.capstone.lovemarker.data.auth.source

import com.capstone.lovemarker.data.auth.dto.request.LoginRequest
import com.capstone.lovemarker.data.auth.dto.response.LoginResponse
import com.capstone.lovemarker.core.network.model.BaseResponse

interface AuthDataSource {
    suspend fun postLogin(
        socialToken: String,
        requestBody: LoginRequest,
    ): BaseResponse<LoginResponse>
}
