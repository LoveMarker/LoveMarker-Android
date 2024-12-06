package com.capstone.lovemarker.feature.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.datastore.source.user.UserDataStore
import com.capstone.lovemarker.domain.nickname.repository.NicknameRepository
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
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val nicknameRepository: NicknameRepository,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(NicknameState())
    val state: StateFlow<NicknameState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<NicknameSideEffect>()
    val sideEffect: SharedFlow<NicknameSideEffect> = _sideEffect.asSharedFlow()

    init {
        // todo: 이전 화면이 로그인이면 디폴트 문자열, 마이페이지인 경우 서버로부터 받아오기
    }

    private fun updatePlaceholder(placeholder: String) {
        _state.update {
            it.copy(
                placeholder = placeholder
            )
        }
    }

    fun updateNickname(nickname: String) {
        _state.update {
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
        _state.update {
            it.copy(uiState = uiState)
        }
    }

    fun updateSupportingText(text: String) {
        _state.update {
            it.copy(supportingText = text)
        }
    }

    fun updateCompleteButtonEnabled(enabled: Boolean) {
        _state.update {
            it.copy(completeButtonEnabled = enabled)
        }
    }

    fun patchNickname(nickname: String) {
        viewModelScope.launch {
            nicknameRepository.patchNickname(nickname)
                .onSuccess { response ->
                    updateInputUiState(
                        InputUiState.Success(response.nickname)
                    )
                }.onFailure { throwable ->
                    val errorBody = (throwable as? HttpException)?.response()?.errorBody()?.string()
                    if (errorBody?.contains(NICKNAME_DUPLICATED_ERR_MSG) == true) {
                        updateInputUiState(InputUiState.Error.DUPLICATED)
                    } else {
                        Timber.e(throwable.message)
                        _sideEffect.emit(NicknameSideEffect.ShowErrorSnackbar(throwable))
                    }
                }
        }
    }

    fun triggerMatchingNavigationEffect() {
        viewModelScope.launch {
            _sideEffect.emit(NicknameSideEffect.NavigateToMatching)
        }
    }

    fun triggerMyPageNavigationEffect(nickname: String) {
        viewModelScope.launch {
            _sideEffect.emit(NicknameSideEffect.NavigateToMyPage(nickname))
        }
    }

    /**
     * from Login vs from MyPage
     * 이전 화면에 따라 달라져야 하는 state
     * */
    fun updateGuideTitle(guideTitle: String) {
        _state.update {
            it.copy(guideTitle = guideTitle)
        }
    }

    fun updateCompleteButtonText(text: String) {
        _state.update {
            it.copy(completeButtonText = text)
        }
    }

    fun updateCloseButtonVisibility(visible: Boolean) {
        _state.update {
            it.copy(closeButtonVisible = visible)
        }
    }

    companion object {
        private const val REGEX_PATTERN = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$"
        private const val NICKNAME_DUPLICATED_ERR_MSG = "중복"
        private const val DEFAULT_PLACE_HOLDER = "닉네임"
    }
}
