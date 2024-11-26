package com.capstone.lovemarker.feature.map

sealed interface MapSideEffect {
    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : MapSideEffect
}
