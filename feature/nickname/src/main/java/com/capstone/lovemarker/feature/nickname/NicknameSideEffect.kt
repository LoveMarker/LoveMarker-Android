package com.capstone.lovemarker.feature.nickname

sealed interface NicknameSideEffect {
    data object NavigateToMatching : NicknameSideEffect

    data class NavigateToMyPage(
        val nickname: String
    ) : NicknameSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : NicknameSideEffect
}
