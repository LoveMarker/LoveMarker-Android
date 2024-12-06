package com.capstone.lovemarker.feature.main.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.domain.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect: SharedFlow<SplashSideEffect> get() = _sideEffect.asSharedFlow()

    fun checkAutoLogin() {
        viewModelScope.launch {
            if (authRepository.isAutoLoginEnabled()) {
                _sideEffect.emit(SplashSideEffect.NavigateToMap)
            } else {
                _sideEffect.emit(SplashSideEffect.NavigateToLogin)
            }
        }
    }
}
