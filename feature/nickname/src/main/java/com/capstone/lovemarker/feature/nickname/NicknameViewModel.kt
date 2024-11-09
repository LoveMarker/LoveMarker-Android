package com.capstone.lovemarker.feature.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NicknameViewModel : ViewModel() {
    private val _nicknameState = MutableStateFlow(NicknameState())
    val nicknameState: StateFlow<NicknameState> = _nicknameState.asStateFlow()

    private val _nicknameSideEffect = MutableSharedFlow<NicknameSideEffect>()
    val nicknameSideEffect: SharedFlow<NicknameSideEffect> = _nicknameSideEffect.asSharedFlow()

    fun updateNickname(nickname: String){
        _nicknameState.update {
            it.copy(nickname = nickname)
        }
    }

    fun validateNickname(nickname: String): Boolean {
        // todo: 공백이나 특수문자 포함하지 않는지 검사
        return true
    }

    fun updateGuideTitle(guideTitle: String) {
        _nicknameState.update {
            it.copy(guideTitle = guideTitle)
        }
    }

    fun updateCompleteButtonText(text: String){
        _nicknameState.update {
            it.copy(completeButtonText = text)
        }
    }

    fun updateCompleteButtonEnabled(enabled: Boolean) {
        _nicknameState.update {
            it.copy(completeButtonEnabled = enabled)
        }
    }

    fun updateCloseButtonVisibility(visible: Boolean) {
        _nicknameState.update {
            it.copy(closeButtonVisible = visible)
        }
    }

    fun patchNickname(nickname: String) {
        viewModelScope.launch {
            // todo: 서버 API 호출
        }
    }
}
