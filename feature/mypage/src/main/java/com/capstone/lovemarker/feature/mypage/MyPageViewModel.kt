package com.capstone.lovemarker.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val myPageRepository: MyPageRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state: StateFlow<MyPageState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect: SharedFlow<MyPageSideEffect> = _sideEffect.asSharedFlow()

    fun updateDisconnectDialogState(showDialog: Boolean) {
        _state.update {
            it.copy(showDisconnectDialog = showDialog)
        }
    }

    fun deleteCouple() {
        viewModelScope.launch {
            myPageRepository.deleteCouple()
                .onSuccess {
                    // todo: disconnect 상태를 데이터스토어에 저장
                    Timber.d("success delete")
                }
                .onFailure { throwable ->
                    Timber.e(throwable.message)
                    _sideEffect.emit(MyPageSideEffect.ShowErrorSnackbar(throwable))
                }
        }
    }
}

