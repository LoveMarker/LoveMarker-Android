package com.capstone.lovemarker.feature.map

import com.google.android.gms.maps.model.LatLng

sealed interface MapSideEffect {
    data class MoveCurrentLocation(
        val location: LatLng
    ): MapSideEffect

    data class NavigateToDetail(
        val memoryId: Int
    ): MapSideEffect

    data object NavigateToPhoto: MapSideEffect
    data object NavigateToMatching: MapSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ): MapSideEffect
}
