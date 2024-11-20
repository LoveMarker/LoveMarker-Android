package com.capstone.lovemarker.feature.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber

class MapViewModel : ViewModel() {
    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

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
