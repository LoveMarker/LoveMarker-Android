package com.capstone.lovemarker.feature.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.navigation.Route
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

    private fun updateInputUiState(uiState: InputUiState) {
        _nicknameState.update {
            it.copy(uiState = uiState)
        }
    }

    fun updateNickname(nickname: String) {
        _nicknameState.update {
            it.copy(nickname = nickname)
        }
    }

    // TODO: 공백이나 특수문자 포함하지 않는지 검사
    fun validateNickname(nickname: String) {
        if (nickname.isNotBlank()) {
            updateInputUiState(uiState = InputUiState.Valid)
        } else {
            updateInputUiState(uiState = InputUiState.Error.NOT_ALLOWED_CHAR)
        }
    }

    fun updateSupportingText(text: String) {
        _nicknameState.update {
            it.copy(supportingText = text)
        }
    }

    fun updateCompleteButtonEnabled(enabled: Boolean) {
        _nicknameState.update {
            it.copy(completeButtonEnabled = enabled)
        }
    }

    fun patchNickname(nickname: String) {
        viewModelScope.launch {
            runCatching {

            }.onSuccess {
                updateInputUiState(
                    InputUiState.Success(input = nicknameState.value.nickname)
                )
            }.onFailure {
                updateInputUiState(
                    InputUiState.Error.DUPLICATED
                )
            }
        }
    }

    /**
     * from Login vs from MyPage
     * 이전 화면에 따라 달라져야 하는 state
     * */
    fun updateGuideTitle(guideTitle: String) {
        _nicknameState.update {
            it.copy(guideTitle = guideTitle)
        }
    }

    fun updateCompleteButtonText(text: String) {
        _nicknameState.update {
            it.copy(completeButtonText = text)
        }
    }

    fun updateCloseButtonVisibility(visible: Boolean) {
        _nicknameState.update {
            it.copy(closeButtonVisible = visible)
        }
    }
}
