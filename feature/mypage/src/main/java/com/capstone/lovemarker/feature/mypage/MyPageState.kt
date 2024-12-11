package com.capstone.lovemarker.feature.mypage

data class MyPageState(
    val nickname: String = "",
    val anniversaryDays: Int = 0,
    val coupleConnected: Boolean = false,
    val partnerNickname: String = "",
    val showDisconnectDialog: Boolean = false,
    val showLogoutDialog: Boolean = false,
)
