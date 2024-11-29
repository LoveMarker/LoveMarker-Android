package com.capstone.lovemarker.data.detail.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.detail.dto.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("/api/memory/{memoryId}")
    suspend fun getDetail(
        @Path("memoryId") memoryId: Long
    ): BaseResponse<DetailResponse>
}
