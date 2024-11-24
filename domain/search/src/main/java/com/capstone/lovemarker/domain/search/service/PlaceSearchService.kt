package com.capstone.lovemarker.domain.search.service

import com.capstone.lovemarker.domain.search.entity.PlaceEntity

interface PlaceSearchService {
    suspend fun getSearchPlaces(keyword: String): List<PlaceEntity>
}
