package com.capstone.lovemarker.feature.mypage

import com.capstone.lovemarker.feature.mypage.model.CoupleModel

data class MyPageState(
    val nickname: String = "", // todo: 닉네임 변경
    val coupleModel: CoupleModel = CoupleModel(),
    val showDisconnectDialog: Boolean = false,
    val showLogoutDialog: Boolean = false,
)
