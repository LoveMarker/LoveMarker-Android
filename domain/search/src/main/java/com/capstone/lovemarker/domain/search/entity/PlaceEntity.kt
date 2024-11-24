package com.capstone.lovemarker.domain.search.entity

data class PlaceEntity(
    val name: String,
    val address: String,
    val location: Pair<Double, Double>,
)
