package com.capstone.lovemarker.feature.map

import com.google.android.gms.maps.model.LatLng

data class MapState(
    val currentLocation: LatLng = LatLng(37.6173, 127.0777)
)
