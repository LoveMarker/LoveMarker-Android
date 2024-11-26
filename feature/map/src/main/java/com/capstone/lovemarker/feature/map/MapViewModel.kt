package com.capstone.lovemarker.feature.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class MapViewModel : ViewModel() {
    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MapSideEffect>()
    val sideEffect: SharedFlow<MapSideEffect> = _sideEffect.asSharedFlow()

    fun updateUserLocation(latLng: LatLng) {
        _state.update {
            it.copy(
                currentLocation = latLng
            )
        }
    }

    fun moveCurrentLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                updateUserLocation(latLng = LatLng(location.latitude, location.longitude))
            }
            .addOnFailureListener { throwable ->
                Timber.e(throwable.message)
            }
    }

    // 퍼미션 거부되었을 때, 디폴트 위치로 과기대 표시
    fun moveDefaultLocation() {
        updateUserLocation(latLng = LatLng(37.6173, 127.0777))
    }
}
