package com.capstone.lovemarker.feature.login

sealed interface LoginSideEffect {
    data object NavigateToNickname : LoginSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : LoginSideEffect
}
