package com.capstone.lovemarker.feature.main.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.domain.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _splashSideEffect = MutableSharedFlow<SplashSideEffect>()
    val splashSideEffect: SharedFlow<SplashSideEffect> = _splashSideEffect.asSharedFlow()

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            if (authRepository.isAutoLoginEnabled()) {
                _splashSideEffect.emit(SplashSideEffect.NavigateToMap)
            } else {
                _splashSideEffect.emit(SplashSideEffect.NavigateToLogin)
            }
        }
    }
}
