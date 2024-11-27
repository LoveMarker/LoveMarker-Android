package com.capstone.lovemarker.feature.mypage.model

import com.capstone.lovemarker.domain.mypage.entity.MyPageEntity

data class CoupleModel(
    val anniversaryDays: Int = 0,
    val connected: Boolean = false,
    val partnerNickname: String = "",
)

fun MyPageEntity.toCoupleModel() = CoupleModel(
    anniversaryDays = anniversaryDays,
    connected = connected,
    partnerNickname = partnerNickname
)
