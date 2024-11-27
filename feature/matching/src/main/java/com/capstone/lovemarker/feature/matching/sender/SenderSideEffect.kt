package com.capstone.lovemarker.feature.matching.sender

sealed interface SenderSideEffect {
    data object NavigateToMap: SenderSideEffect

    data class ShowShareDialog(
        val invitationCode: String
    ): SenderSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : SenderSideEffect
}
