package com.capstone.lovemarker.feature.matching.receiver

sealed interface ReceiverSideEffect {
    data object NavigateToMap: ReceiverSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : ReceiverSideEffect
}