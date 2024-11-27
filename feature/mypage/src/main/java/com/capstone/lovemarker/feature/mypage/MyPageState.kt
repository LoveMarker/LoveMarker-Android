package com.capstone.lovemarker.feature.mypage

data class MyPageState(
    val nickname: String = "",
    val showDisconnectDialog: Boolean = false,
    val showLogoutDialog: Boolean = false,
)
