package com.capstone.lovemarker.map.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.map.dto.MapResponse

interface MapDataSource {
    suspend fun getMemories(
        radius: Double,
        latitude: Double,
        longitude: Double
    ): BaseResponse<MapResponse>
}
