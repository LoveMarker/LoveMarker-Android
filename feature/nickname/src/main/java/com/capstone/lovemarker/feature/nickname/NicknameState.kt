package com.capstone.lovemarker.feature.nickname

data class NicknameState(
    val nickname: String = "",
    val guideTitle: String = "",
    val completeButtonText: String = "",
    val completeButtonEnabled: Boolean = false,
    val closeButtonVisible: Boolean = false,
)
