package com.capstone.lovemarker.feature.nickname

data class NicknameState(
    val closeButtonVisible: Boolean = false,
    val guideTitle: String = "",
    val nickname: String = "",
    val buttonText: String = "",
    val buttonEnabled: Boolean = false,
)
