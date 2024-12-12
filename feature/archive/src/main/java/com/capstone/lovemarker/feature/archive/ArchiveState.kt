package com.capstone.lovemarker.feature.archive

import com.capstone.lovemarker.core.common.util.UiState

data class ArchiveState(
    val uiState: UiState<Unit> = UiState.Empty,
    val coupleConnected: Boolean = true,
    val showMatchingDialog: Boolean = false
)
