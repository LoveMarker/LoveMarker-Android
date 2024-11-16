package com.capstone.lovemarker.feature.login

sealed interface LoginSideEffect {
    data class LoginSuccess(
        val isRegistered: Boolean
    ) : LoginSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : LoginSideEffect
}
