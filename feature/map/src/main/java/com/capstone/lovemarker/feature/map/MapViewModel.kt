package com.capstone.lovemarker.feature.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.common.util.UiState
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStore
import com.capstone.lovemarker.core.datastore.source.user.UserDataStore
import com.capstone.lovemarker.domain.mypage.repository.MyPageRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
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
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class MapViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val userDataStore: UserDataStore,
    private val coupleDataStore: CoupleDataStore,
) : ViewModel() {
    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MapSideEffect>()
    val sideEffect: SharedFlow<MapSideEffect> = _sideEffect.asSharedFlow()

    init {
        // 다른 화면에서 데이터 스토어 활용할 수 있도록 데이터 초기화
        getCoupleInfo()
    }

    private fun getCoupleInfo() {
        viewModelScope.launch {
            updateUiState(UiState.Loading)

            myPageRepository.getCoupleInfo()
                .onSuccess { response ->
                    userDataStore.updateNickname(response.nickname)
                    coupleDataStore.updateCoupleConnectState(response.connected)
                    updateUiState(UiState.Success(Unit))
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }

    private fun updateUiState(uiState: UiState<Unit>) {
        _state.update {
            it.copy(
                uiState = uiState
            )
        }
    }

    suspend fun getUserLocation(fusedLocationClient: FusedLocationProviderClient): Location =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    continuation.resume(location)
                }
                .addOnFailureListener { throwable ->
                    Timber.e(throwable.message)
                    continuation.resumeWithException(throwable)
                }
        }

    fun updateCurrentLocation(location: LatLng) {
        _state.update {
            it.copy(
                currentLocation = location
            )
        }
    }

    fun getCoupleConnectState(): Deferred<Boolean> {
        return viewModelScope.async {
            coupleDataStore.coupleData.first().connected
        }
    }

    fun updateCoupleConnectState(connected: Boolean) {
        _state.update {
            it.copy(coupleConnected = connected)
        }
    }

    fun updateMatchingDialogState(showDialog: Boolean) {
        _state.update {
            it.copy(
                showMatchingDialog = showDialog
            )
        }
    }

    fun triggerMoveCurrentLocationEffect(location: Location) {
        viewModelScope.launch {
            _sideEffect.emit(
                MapSideEffect.MoveCurrentLocation(
                    location = LatLng(location.latitude, location.longitude)
                )
            )
        }
    }

    fun triggerMatchingNavigationEffect() {
        viewModelScope.launch {
            _sideEffect.emit(MapSideEffect.NavigateToMatching)
        }
    }

    fun triggerPhotoNavigationEffect() {
        viewModelScope.launch {
            _sideEffect.emit(MapSideEffect.NavigateToPhoto)
        }
    }
}
