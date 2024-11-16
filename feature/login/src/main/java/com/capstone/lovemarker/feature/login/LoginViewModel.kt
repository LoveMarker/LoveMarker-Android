package com.capstone.lovemarker.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.domain.auth.entity.Token
import com.capstone.lovemarker.domain.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _loginSideEffect = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffect: SharedFlow<LoginSideEffect> = _loginSideEffect.asSharedFlow()

    fun postLogin(socialToken: String) {
        Timber.d("GOOGLE TOKEN: $socialToken")

        viewModelScope.launch {
            authRepository.postLogin(
                socialToken = socialToken,
                provider = OAUTH_PROVIDER
            ).onSuccess { response ->
                authRepository.apply {
                    saveTokens(
                        Token(
                            accessToken = response.accessToken,
                            refreshToken = response.refreshToken
                        )
                    )
                    updateAutoLogin(configured = true)

                    Timber.d("Server Access Token: ${response.accessToken}")
                    Timber.d("Server Refresh Token: ${response.refreshToken}")
                }

                _loginSideEffect.emit(LoginSideEffect.LoginSuccess(response.isRegistered))
            }.onFailure {
                _loginSideEffect.emit(LoginSideEffect.ShowErrorSnackbar(it))
            }
        }
    }

    companion object {
        private const val OAUTH_PROVIDER = "GOOGLE"
    }
}