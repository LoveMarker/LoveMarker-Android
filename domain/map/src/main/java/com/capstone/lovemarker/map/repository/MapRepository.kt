package com.capstone.lovemarker.map.repository

import com.capstone.lovemarker.map.entity.MemoryEntity

interface MapRepository {
    suspend fun getMemories(
        radius: Double,
        latitude: Double,
        longitude: Double
    ): Result<List<MemoryEntity>>
}
