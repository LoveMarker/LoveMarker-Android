package com.capstone.lovemarker.data.search.source

import com.google.android.libraries.places.api.model.Place

interface PlaceSearchDataSource {
    suspend fun getSearchPlaces(keyword: String): List<Place>
}
