package com.capstone.lovemarker.feature.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.domain.nickname.repository.NicknameRepository
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
class NicknameViewModel @Inject constructor(
    private val nicknameRepository: NicknameRepository,
) : ViewModel() {
    private val _nicknameState = MutableStateFlow(NicknameState())
    val nicknameState: StateFlow<NicknameState> = _nicknameState.asStateFlow()

    private val _nicknameSideEffect = MutableSharedFlow<NicknameSideEffect>()
    val nicknameSideEffect: SharedFlow<NicknameSideEffect> = _nicknameSideEffect.asSharedFlow()

    fun updateNickname(nickname: String) {
        _nicknameState.update {
            it.copy(nickname = nickname)
        }
        validateNickname(nickname)
    }

    private fun validateNickname(nickname: String) {
        if (nickname.isEmpty()) {
            updateInputUiState(uiState = InputUiState.Empty)
            return
        }

        if (nickname.matches(REGEX_PATTERN.toRegex())) {
            updateInputUiState(uiState = InputUiState.Valid)
        } else {
            updateInputUiState(uiState = InputUiState.Error.NOT_ALLOWED_CHAR)
        }
    }

    private fun updateInputUiState(uiState: InputUiState) {
        _nicknameState.update {
            it.copy(uiState = uiState)
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
            updateInputUiState(
                InputUiState.Error.DUPLICATED
            )

//            nicknameRepository.patchNickname(nickname)
//                .onSuccess {
//                    updateInputUiState(
//                        InputUiState.Success
//                    )
//                }.onFailure {
//                    updateInputUiState(
//                        InputUiState.Error.DUPLICATED
//                    )
//                }
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

    companion object {
        private const val REGEX_PATTERN = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$"
    }
}
