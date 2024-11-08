package com.capstone.lovemarker.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReissueTokenResponse(
    @SerialName("accessToken")
    val accessToken: String,
)