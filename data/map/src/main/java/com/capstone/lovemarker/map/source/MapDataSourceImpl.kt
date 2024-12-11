package com.capstone.lovemarker.map.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.map.dto.MapResponse
import com.capstone.lovemarker.map.service.MapService
import javax.inject.Inject

class MapDataSourceImpl @Inject constructor(
    private val mapService: MapService
) : MapDataSource {
    override suspend fun getMemories(
        radius: Double,
        latitude: Double,
        longitude: Double
    ): BaseResponse<MapResponse> = mapService.getMemories(radius, latitude, longitude)
}
