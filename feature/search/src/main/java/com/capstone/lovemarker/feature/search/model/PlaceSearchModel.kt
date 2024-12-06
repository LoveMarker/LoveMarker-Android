package com.capstone.lovemarker.feature.search.model

import com.capstone.lovemarker.core.model.SearchPlace

data class PlaceSearchModel(
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    fun toCoreModel() = SearchPlace(
        name = name,
        address = address,
        latitude = latitude,
        longitude = longitude
    )
}
