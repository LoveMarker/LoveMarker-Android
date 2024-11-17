package com.capstone.lovemarker.feature.matching.sender

sealed interface SenderSideEffect {
    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : SenderSideEffect
}