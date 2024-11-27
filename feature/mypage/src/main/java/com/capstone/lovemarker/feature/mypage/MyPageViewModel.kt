package com.capstone.lovemarker.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStore
import com.capstone.lovemarker.core.datastore.source.user.UserDataStore
import com.capstone.lovemarker.domain.mypage.repository.MyPageRepository
import com.capstone.lovemarker.feature.mypage.model.CoupleModel
import com.capstone.lovemarker.feature.mypage.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

    init {
        initUserNickname()
        getCoupleInfo()
    }

    private fun initUserNickname() {
        viewModelScope.launch {
            _state.update {
                it.copy(nickname = userDataStore.userData.first().nickname)
            }
        }
    }

    private fun getCoupleInfo() {
        viewModelScope.launch {
            myPageRepository.getCoupleInfo()
                .onSuccess { couple ->
                    updateCoupleModel(couple.toModel())
                }.onFailure {
                    _sideEffect.emit(MyPageSideEffect.ShowErrorSnackbar(it))
                }
        }
    }

    private fun updateCoupleModel(coupleModel: CoupleModel) {
        _state.update {
            it.copy(
                coupleModel = coupleModel
            )
        }
    }

    fun updateDisconnectDialogState(showDialog: Boolean) {
        _state.update {
            it.copy(showDisconnectDialog = showDialog)
        }
    }

    fun deleteCouple() {
        viewModelScope.launch {
            myPageRepository.deleteCouple()
                .onSuccess {
                    updateCoupleModel(
                        state.value.coupleModel.copy(
                            connected = false
                        )
                    )

                    // todo: 다른 화면에서도 커플 연결 상태를 조회하기 위해서 저장
                    coupleDataStore.updateConnectedState(connected = false)
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _sideEffect.emit(MyPageSideEffect.ShowErrorSnackbar(throwable))
                }
        }
    }
}

