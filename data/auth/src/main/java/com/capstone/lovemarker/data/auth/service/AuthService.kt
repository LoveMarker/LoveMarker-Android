package com.capstone.lovemarker.data.auth.service

import com.capstone.lovemarker.data.auth.dto.request.LoginRequest
import com.capstone.lovemarker.data.auth.dto.response.LoginResponse
import com.capstone.lovemarker.core.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth")
    suspend fun postLogin(
        @Body requestBody: LoginRequest,
    ): BaseResponse<LoginResponse>
}
