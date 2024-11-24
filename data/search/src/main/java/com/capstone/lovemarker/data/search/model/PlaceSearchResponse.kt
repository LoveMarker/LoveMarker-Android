package com.capstone.lovemarker.data.search.model

import com.capstone.lovemarker.domain.search.entity.PlaceEntity

typealias Place = com.google.android.libraries.places.api.model.Place

fun Place.toDomain() = PlaceEntity(
    name = this.displayName ?: "",
    address = this.formattedAddress ?: "",
    location = this.location?.let {
        Pair(it.latitude, it.longitude)
    } ?: Pair(0.0, 0.0)
)
