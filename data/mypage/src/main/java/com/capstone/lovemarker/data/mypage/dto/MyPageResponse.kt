package com.capstone.lovemarker.data.mypage.dto

import com.capstone.lovemarker.domain.mypage.entity.CoupleEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageResponse(
    @SerialName("isMatched")
    val isMatched: Boolean,
    @SerialName("anniversaryDays")
    val anniversaryDays: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("partnerNickname")
    val partnerNickname: String,
) {
    fun toDomain() = CoupleEntity(
        partnerNickname = partnerNickname,
        anniversaryDays = anniversaryDays,
        connected = isMatched
    )
}
