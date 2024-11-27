package com.capstone.lovemarker.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.datastore.source.user.UserDataStore
import com.capstone.lovemarker.domain.mypage.repository.MyPageRepository
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
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state: StateFlow<MyPageState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect: SharedFlow<MyPageSideEffect> = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            updateNickname(userDataStore.user.first().nickname)
            // todo: 데이터 스토어에서 가져오기 (상대방 닉네임, 커플 연결 정보)
        }
    }

    fun updateDisconnectDialogState(showDialog: Boolean) {
        _state.update {
            it.copy(showDisconnectDialog = showDialog)
        }
    }

    fun updateNickname(nickname: String) {
        _state.update {
            it.copy(nickname = nickname)
        }
    }

    fun getCoupleInfo() {
        viewModelScope.launch {
            // todo: 데이터 스토어에 상대방 닉네임, 커플 연결 상태 저장
        }
    }

    fun deleteCouple() {
        viewModelScope.launch {
            myPageRepository.deleteCouple()
                .onSuccess {
                    // todo: 커플 연결 상태 업데이트 (데이터 스토어)
                    Timber.d("success delete")
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _sideEffect.emit(MyPageSideEffect.ShowErrorSnackbar(throwable))
                }
        }
    }
}

