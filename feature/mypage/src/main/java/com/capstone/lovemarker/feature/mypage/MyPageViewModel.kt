package com.capstone.lovemarker.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStore
import com.capstone.lovemarker.core.datastore.source.user.UserDataStore
import com.capstone.lovemarker.domain.mypage.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val userDataStore: UserDataStore,
    private val coupleDataStore: CoupleDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state: StateFlow<MyPageState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect: SharedFlow<MyPageSideEffect> = _sideEffect.asSharedFlow()

    fun getMyPageInfo() {
        viewModelScope.launch {
            myPageRepository.getMyPageInfo()
                .onSuccess { response ->
                    // 매칭 여부와 상관없이 표시
                    updateNickname(response.nickname)
                    updateAnniversaryDays(response.anniversaryDays)

                    // 매칭 되었을 때만 표시
                    updateCoupleConnectState(response.connected)
                    updatePartnerNickname(response.partnerNickname)
                }.onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowErrorSnackbar(it))
                }
        }
    }

    private fun updateNickname(nickname: String) {
        _state.update {
            it.copy(nickname = nickname)
        }
    }

    private fun updateAnniversaryDays(days: Int) {
        _state.update {
            it.copy(anniversaryDays = days)
        }
    }

    private fun updateCoupleConnectState(connected: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(coupleConnected = connected)
            }
        }
    }

    private fun updatePartnerNickname(nickname: String) {
        _state.update {
            it.copy(partnerNickname = nickname)
        }
    }

    fun updateDisconnectDialogState(showDialog: Boolean) {
        _state.update {
            it.copy(showDisconnectDialog = showDialog)
        }
    }

    fun updateLogoutDialogState(showDialog: Boolean) {
        _state.update {
            it.copy(
                showLogoutDialog = showDialog
            )
        }
    }

    fun clearUserDataStore() {
        viewModelScope.launch {
            userDataStore.clear()
        }
    }

    fun deleteCouple() {
        viewModelScope.launch {
            myPageRepository.deleteCouple()
                .onSuccess {
                    coupleDataStore.updateCoupleConnectState(connected = false)
                    updateCoupleConnectState(connected = false)
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _sideEffect.emit(MyPageSideEffect.ShowErrorSnackbar(throwable))
                }
        }
    }

    fun triggerNicknameNavigationEffect() {
        viewModelScope.launch {
            _sideEffect.emit(
                MyPageSideEffect.NavigateToNickname(nickname = state.value.nickname)
            )
        }
    }

    fun triggerMatchingNavigationEffect() {
        viewModelScope.launch {
            _sideEffect.emit(
                MyPageSideEffect.NavigateToMatching
            )
        }
    }

    fun triggerMyFeedNavigationEffect() {
        viewModelScope.launch {
            _sideEffect.emit(MyPageSideEffect.NavigateToMyFeed)
        }
    }

    fun triggerRestartAppEffect() {
        viewModelScope.launch {
            _sideEffect.emit(MyPageSideEffect.RestartApp)
        }
    }
}
