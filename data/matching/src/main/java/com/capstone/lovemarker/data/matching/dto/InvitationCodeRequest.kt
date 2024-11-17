package com.capstone.lovemarker.data.matching.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvitationCodeRequest(
    @SerialName("anniversary")
    val anniversary: String
)
