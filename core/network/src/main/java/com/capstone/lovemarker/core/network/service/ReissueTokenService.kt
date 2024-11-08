package com.capstone.lovemarker.core.network.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.core.network.model.ReissueTokenResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ReissueTokenService {
    @GET("/auth/reissue-token")
    fun getAccessToken(
        @Header("refreshToken") refreshToken: String,
    ): BaseResponse<ReissueTokenResponse>
}
