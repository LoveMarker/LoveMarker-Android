package com.capstone.lovemarker.map.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.map.dto.MapResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {
    @GET("/api/memory/map-view")
    suspend fun getMemories(
        @Query("radius") radius: Double,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): BaseResponse<MapResponse>
}
