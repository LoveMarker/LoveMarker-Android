package com.capstone.lovemarker.domain.search.service

import com.capstone.lovemarker.core.model.SearchPlace

interface PlaceSearchService {
    suspend fun getSearchPlaces(keyword: String): List<SearchPlace>
}
