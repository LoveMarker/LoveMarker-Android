package com.capstone.lovemarker.data.mypage.dto

import com.capstone.lovemarker.domain.mypage.entity.MyPageEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageResponse(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("isMatched")
    val isMatched: Boolean,
    @SerialName("anniversaryDays")
    val anniversaryDays: Int?,
    @SerialName("partnerNickname")
    val partnerNickname: String?,
) {
    fun toDomain() = MyPageEntity(
        nickname = nickname,
        connected = isMatched,
        anniversaryDays = anniversaryDays ?: 0,
        partnerNickname = partnerNickname ?: "나의 그대"
    )
}
