package com.capstone.lovemarker.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.feature.login.service.GoogleAuthService
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleAuthService: GoogleAuthService,
) : ViewModel() {
    private val _loginSideEffect = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffect: SharedFlow<LoginSideEffect> = _loginSideEffect.asSharedFlow()

    fun signIn() {
        viewModelScope.launch {
            runCatching {
                googleAuthService.signIn()
            }.onSuccess { token ->
                postLogin(token.accessToken)
            }.onFailure { throwable ->
                Timber.e(throwable.message)
                _loginSideEffect.emit(LoginSideEffect.ShowErrorSnackbar(throwable))
            }
        }
    }

    private fun postLogin(accessToken: String) {
        Timber.d(accessToken)
        // todo: 서버 연결 -> 성공/실패 여부에 따라 사이드 이펙트 발생 -> UI에서 처리
    }
}