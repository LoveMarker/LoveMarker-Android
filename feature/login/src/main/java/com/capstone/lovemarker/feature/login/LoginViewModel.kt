package com.capstone.lovemarker.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginSideEffect = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffect: SharedFlow<LoginSideEffect> = _loginSideEffect.asSharedFlow()

    fun postLogin(socialToken: String) {
        Timber.d("GOOGLE TOKEN: $socialToken")

        viewModelScope.launch {
            _loginSideEffect.emit(LoginSideEffect.NavigateToNickname)

//            authRepository.postLogin(
//                socialToken = socialToken,
//                provider = OAUTH_PROVIDER
//            ).onSuccess {
//                _loginSideEffect.emit(LoginSideEffect.NavigateToNickname)
//            }.onFailure {
//                _loginSideEffect.emit(LoginSideEffect.ShowErrorSnackbar(it))
//            }
        }
    }

    companion object {
        private const val OAUTH_PROVIDER = "GOOGLE"
    }
}