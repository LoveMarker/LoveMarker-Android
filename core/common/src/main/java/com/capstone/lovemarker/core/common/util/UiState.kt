package com.capstone.lovemarker.core.common.util

sealed interface UiState<out T> {
    data object Empty : UiState<Nothing>

    data object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T,
    ) : UiState<T>

    data class Failure(
        val throwable: Throwable,
    ) : UiState<Nothing>
}
