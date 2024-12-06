package com.capstone.lovemarker.feature.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val coupleDataStore: CoupleDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MapSideEffect>()
    val sideEffect: SharedFlow<MapSideEffect> = _sideEffect.asSharedFlow()

    fun initCurrentLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                updateCurrentLocation(
                    location = LatLng(it.latitude, it.longitude)
                )
            }
        }.addOnFailureListener { throwable ->
            Timber.e(throwable.message)
        }
    }

    fun updateCurrentLocation(location: LatLng) {
        _state.update {
            it.copy(currentLocation = location)
        }
    }

    fun triggerPhotoNavigationEffect() {
        viewModelScope.launch {
            _sideEffect.emit(MapSideEffect.NavigateToPhoto)
        }
    }
}

data class MapState(
    val currentLocation: LatLng? = null,
)

sealed interface MapSideEffect {
    data object NavigateToPhoto: MapSideEffect
    data object NavigateToMatching : MapSideEffect
}
