package com.capstone.lovemarker

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.location.Location
import android.os.Looper
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

class MainViewModel(application: Application) :
    AndroidViewModel(application), LifecycleEventObserver {
    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val locationRequest: LocationRequest
    private val locationCallback: MyLocationCallBack

    private val _mapState =
        mutableStateOf(
            MapState(
                location = null,
                polylineOptions = PolylineOptions().width(5f).color(Color.RED)
            )
        )
    val mapState: State<MapState> = _mapState

    init {
        // 현위치를 얻을 때 필요한 설정을 저장하는 객체
        locationRequest = LocationRequest.Builder(10000) // 10초 간격으로 업데이트
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY) // 정확한 위치
            .setMinUpdateIntervalMillis(5000) // 최소 5초 간격
            .build()

        // 위치 정보를 얻었을 때의 동작 정의
        locationCallback = MyLocationCallBack()
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val location = locationResult.lastLocation
            val polylineOptions = mapState.value.polylineOptions

            // 갱신된 현재 위치를 state에 저장
            _mapState.value = mapState.value.copy(
                location = location,
                polylineOptions = polylineOptions.add(
                    LatLng(
                        location?.latitude ?: 0.0,
                        location?.longitude ?: 0.0,
                    )
                )
            )
        }
    }

    // 액티비티 생명주기에 따라 위치 요청 리스너 등록/해제
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_RESUME) {
            addLocationListener()
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            removeLocationListener()
        }
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        Looper.myLooper()?.let { looper ->
            // 주기적인 위치 갱신을 위한 리스너 등록
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                looper,
            )
        }
    }

    private fun removeLocationListener() {
        // 위치 갱신 리스너 해제
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}

data class MapState(
    val location: Location?,
    val polylineOptions: PolylineOptions,
)
