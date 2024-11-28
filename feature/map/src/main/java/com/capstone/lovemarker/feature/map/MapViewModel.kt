package com.capstone.lovemarker.feature.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val coupleDataStore: CoupleDataStore
) : ViewModel() {
    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

    init {
        // todo: 연결 상태에 따라 MapState 갱신
//        viewModelScope.launch {
//            coupleDataStore.coupleData.first().connected
//        }
    }

    fun getUserLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                _userLocation.value = LatLng(it.latitude, it.longitude)
            }
        }.addOnFailureListener { throwable ->
            Timber.e(throwable.message)
        }
    }
}
