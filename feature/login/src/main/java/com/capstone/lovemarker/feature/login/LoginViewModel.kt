package com.capstone.lovemarker.feature.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber

class LoginViewModel: ViewModel() {
    private val _loginSideEffect = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffect: SharedFlow<LoginSideEffect> = _loginSideEffect.asSharedFlow()

    private fun postLogin(accessToken: String) {
        Timber.d(accessToken)
        // todo: 서버 연결 -> 성공/실패 여부에 따라 사이드 이펙트 발생 -> UI에서 처리
    }
}