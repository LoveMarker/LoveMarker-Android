package com.capstone.lovemarker.map.dto

import com.capstone.lovemarker.map.entity.MemoryEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MapResponse(
    @SerialName("memories")
    val memories: List<Memory>
) {
    @Serializable
    data class Memory(
        @SerialName("memoryId")
        val memoryId: Int,
        @SerialName("latitude")
        val latitude: Double,
        @SerialName("longitude")
        val longitude: Double
    )
}

fun MapResponse.toDomain(): List<MemoryEntity> =
    memories.map { memory ->
        MemoryEntity(
            memoryId = memory.memoryId,
            latitude = memory.latitude,
            longitude = memory.longitude
        )
    }
