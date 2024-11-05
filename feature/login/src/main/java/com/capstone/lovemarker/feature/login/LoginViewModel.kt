package com.capstone.lovemarker.feature.login

import androidx.lifecycle.ViewModel
import com.capstone.lovemarker.auth.repository.AuthRepository
import com.capstone.lovemarker.oauth.service.OAuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel : ViewModel() {
    private val _loginSideEffect = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffect: SharedFlow<LoginSideEffect> = _loginSideEffect.asSharedFlow()

    fun postLogin(socialToken: String) {
        Timber.d(socialToken)

        // todo: 서버 연결 -> 성공/실패 여부에 따라 사이드 이펙트 발생 -> UI에서 처리
    }
}