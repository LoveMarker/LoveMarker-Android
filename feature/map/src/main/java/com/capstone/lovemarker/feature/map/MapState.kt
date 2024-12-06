package com.capstone.lovemarker.feature.map

import com.capstone.lovemarker.core.common.util.UiState
import com.google.android.gms.maps.model.LatLng

data class MapState(
    val uiState: UiState<Unit> = UiState.Empty,
    val currentLocation: LatLng? = null,
    val coupleConnected: Boolean = true,
    val showMatchingDialog: Boolean = false,
)
