package com.capstone.lovemarker.core.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchPlace(
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
