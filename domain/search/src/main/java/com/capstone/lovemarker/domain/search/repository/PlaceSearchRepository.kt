package com.capstone.lovemarker.domain.search.repository

import com.capstone.lovemarker.domain.search.entity.PlaceEntity

interface PlaceSearchRepository {
    suspend fun getSearchPlaces(keyword: String): Result<List<PlaceEntity>>
}
