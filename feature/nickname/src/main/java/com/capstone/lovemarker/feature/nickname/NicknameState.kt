package com.capstone.lovemarker.feature.nickname

data class NicknameState(
    val uiState: InputUiState = InputUiState.Blank,
    val nickname: String = "",
    val guideTitle: String = "",
    val supportingText: String = "",
    val completeButtonText: String = "",
    val completeButtonEnabled: Boolean = false,
    val closeButtonVisible: Boolean = false,
)

sealed interface InputUiState {
    data object Blank : InputUiState

    data object Valid : InputUiState

    enum class Error : InputUiState {
        NOT_ALLOWED_CHAR,
        DUPLICATED
    }

    data object Success : InputUiState
}
