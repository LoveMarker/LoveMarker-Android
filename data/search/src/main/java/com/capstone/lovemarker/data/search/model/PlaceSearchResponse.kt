package com.capstone.lovemarker.data.search.model

import com.capstone.lovemarker.core.model.SearchPlace

typealias GoogleMapPlace = com.google.android.libraries.places.api.model.Place

fun GoogleMapPlace.toDomain() = SearchPlace(
    name = this.displayName ?: "",
    address = this.formattedAddress ?: "",
    latitude = this.location?.latitude ?: 0.0,
    longitude = this.location?.latitude ?: 0.0,
)
