package com.capstone.lovemarker.feature.mypage.model

import com.capstone.lovemarker.domain.mypage.entity.CoupleEntity

data class CoupleModel(
    val anniversaryDays: Int = 0,
    val connected: Boolean = false,
    val partnerNickname: String = "",
)

fun CoupleEntity.toModel() = CoupleModel(
    anniversaryDays = anniversaryDays,
    connected = connected,
    partnerNickname = partnerNickname
)
