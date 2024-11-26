package com.capstone.lovemarker.feature.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class MapViewModel : ViewModel() {
    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MapSideEffect>()
    val sideEffect: SharedFlow<MapSideEffect> = _sideEffect.asSharedFlow()

    fun initCurrentLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                updateCurrentLocation(LatLng(location.latitude, location.longitude))
            }
            .addOnFailureListener { throwable ->
                Timber.e(throwable.message)
            }
    }

    fun updateCurrentLocation(latLng: LatLng) {
        _state.update {
            it.copy(
                currentLocation = latLng
            )
        }
    }

    fun moveCurrentLocation() {
        viewModelScope.launch {
            _sideEffect.emit(MapSideEffect.MoveCurrentLocation)
        }
    }

    fun moveCameraPosition(latLng: LatLng) {
        viewModelScope.launch {
            _sideEffect.emit(MapSideEffect.MoveCameraPosition(latLng))
        }
    }
}
