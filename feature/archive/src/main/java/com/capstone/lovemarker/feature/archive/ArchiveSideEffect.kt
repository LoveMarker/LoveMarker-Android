package com.capstone.lovemarker.feature.archive

sealed interface ArchiveSideEffect {
    data class NavigateToDetail(
        val memoryId: Int
    ) : ArchiveSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ) : ArchiveSideEffect
}
