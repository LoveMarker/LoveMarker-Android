package com.capstone.lovemarker.feature.nickname

sealed interface NicknameSideEffect {
    data object NavigateToMyPage : NicknameSideEffect

    data object NavigateToMatching : NicknameSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : NicknameSideEffect
}
