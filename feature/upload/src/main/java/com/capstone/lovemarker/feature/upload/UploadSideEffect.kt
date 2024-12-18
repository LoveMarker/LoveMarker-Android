package com.capstone.lovemarker.feature.upload

sealed interface UploadSideEffect {
    data class NavigateToMap(
        val memoryId: Int
    ) : UploadSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : UploadSideEffect
}
