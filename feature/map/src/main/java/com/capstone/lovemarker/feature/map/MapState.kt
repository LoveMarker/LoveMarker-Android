package com.capstone.lovemarker.feature.map

import com.capstone.lovemarker.core.common.util.UiState
import com.capstone.lovemarker.map.entity.MemoryEntity
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MapState(
    val uiState: UiState<Unit> = UiState.Empty,
    val currentLocation: LatLng? = null,
    val memories: PersistentList<MemoryModel> = persistentListOf(),
    val coupleConnected: Boolean = true,
    val showMatchingDialog: Boolean = false,
)

fun MemoryEntity.toModel() = MemoryModel(
    memoryId = memoryId,
    latitude = latitude,
    longitude = longitude
)

data class MemoryModel(
    val memoryId: Int,
    val latitude: Double,
    val longitude: Double
)
