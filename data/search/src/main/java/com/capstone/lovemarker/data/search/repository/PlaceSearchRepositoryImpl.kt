package com.capstone.lovemarker.data.search.repository

import com.capstone.lovemarker.data.search.dto.toDomain
import com.capstone.lovemarker.data.search.source.PlaceSearchDataSource
import com.capstone.lovemarker.domain.search.entity.PlaceEntity
import com.capstone.lovemarker.domain.search.repository.PlaceSearchRepository
import javax.inject.Inject

class PlaceSearchRepositoryImpl @Inject constructor(
    private val placeSearchDataSource: PlaceSearchDataSource
) : PlaceSearchRepository {
    override suspend fun getSearchPlaces(keyword: String): Result<List<PlaceEntity>> =
        runCatching {
            placeSearchDataSource.getSearchPlaces(keyword).map { place ->
                place.toDomain()
            }
        }
}
