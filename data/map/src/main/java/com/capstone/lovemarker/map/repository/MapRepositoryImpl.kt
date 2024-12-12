package com.capstone.lovemarker.map.repository

import com.capstone.lovemarker.map.dto.toDomain
import com.capstone.lovemarker.map.entity.MemoryEntity
import com.capstone.lovemarker.map.source.MapDataSource
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapDataSource: MapDataSource
) : MapRepository {
    override suspend fun getMemories(
        radius: Double,
        latitude: Double,
        longitude: Double
    ): Result<List<MemoryEntity>> = runCatching {
        mapDataSource.getMemories(radius, latitude, longitude).data.toDomain()
    }
}
