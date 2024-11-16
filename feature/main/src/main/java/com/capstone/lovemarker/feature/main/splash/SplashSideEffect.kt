package com.capstone.lovemarker.feature.main.splash

sealed interface SplashSideEffect {
    data object NavigateToMap: SplashSideEffect
    data object NavigateToLogin: SplashSideEffect
}
