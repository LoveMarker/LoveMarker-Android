package com.capstone.lovemarker.data.matching.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoupleJoinRequest(
    @SerialName("invitationCode")
    val invitationCode: String
)
