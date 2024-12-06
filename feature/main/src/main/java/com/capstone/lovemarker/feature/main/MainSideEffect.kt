package com.capstone.lovemarker.feature.main

sealed interface MainSideEffect {
    data object NavigateToMap: MainSideEffect
    data object NavigateToLogin: MainSideEffect
}
