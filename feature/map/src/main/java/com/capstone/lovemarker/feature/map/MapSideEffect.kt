package com.capstone.lovemarker.feature.map

import com.google.android.gms.maps.model.LatLng

sealed interface MapSideEffect {
    data object MoveCurrentLocation : MapSideEffect

    data class MoveCameraPosition(
        val latLng: LatLng
    ) : MapSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : MapSideEffect
}
