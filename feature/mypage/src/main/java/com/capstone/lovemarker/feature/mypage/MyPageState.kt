package com.capstone.lovemarker.feature.mypage

import com.capstone.lovemarker.feature.mypage.model.CoupleModel

data class MyPageState(
    val nickname: String = "",
    val coupleModel: CoupleModel = CoupleModel(),
    val showDisconnectDialog: Boolean = false,
    val showLogoutDialog: Boolean = false,
)
