package com.capstone.lovemarker.core.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchPlace(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)
