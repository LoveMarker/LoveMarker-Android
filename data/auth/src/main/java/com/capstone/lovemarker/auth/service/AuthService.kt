package com.capstone.lovemarker.auth.service

import com.capstone.lovemarker.auth.dto.request.LoginRequest
import com.capstone.lovemarker.auth.dto.response.LoginResponse
import com.capstone.lovemarker.core.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("auth")
    suspend fun postLogin(
        @Header("Authorization") socialToken: String,
        @Body requestBody: LoginRequest,
    ): BaseResponse<LoginResponse>
}