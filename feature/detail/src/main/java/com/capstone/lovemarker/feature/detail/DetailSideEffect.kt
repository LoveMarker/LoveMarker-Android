package com.capstone.lovemarker.feature.detail

sealed interface DetailSideEffect {
    data class ShowErrorSnackbar(
        val throwable: Throwable
    ): DetailSideEffect
}