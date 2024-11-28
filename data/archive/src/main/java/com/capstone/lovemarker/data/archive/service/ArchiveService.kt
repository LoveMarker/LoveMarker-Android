package com.capstone.lovemarker.data.archive.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.archive.dto.response.ArchiveResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArchiveService {
    @GET("/api/memory/list-view")
    suspend fun getMemories(
        @Query("page") pageIndex: Int,
        @Query("size") itemsPerPage: Int,
    ): BaseResponse<ArchiveResponse>
}
