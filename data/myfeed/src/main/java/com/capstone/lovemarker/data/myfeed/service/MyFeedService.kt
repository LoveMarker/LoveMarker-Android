package com.capstone.lovemarker.data.myfeed.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.myfeed.dto.MyFeedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyFeedService {
    @GET("/api/memory/me")
    suspend fun getMemories(
        @Query("page") pageIndex: Int,
        @Query("size") itemsPerPage: Int,
    ): BaseResponse<MyFeedResponse>
}
