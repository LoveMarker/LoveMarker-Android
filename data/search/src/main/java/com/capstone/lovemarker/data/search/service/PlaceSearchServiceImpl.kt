package com.capstone.lovemarker.data.search.service

import android.content.Context
import com.capstone.lovemarker.data.search.model.toDomain
import com.capstone.lovemarker.domain.search.entity.PlaceSearchEntity
import com.capstone.lovemarker.domain.search.service.PlaceSearchService
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.SearchByTextRequest
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PlaceSearchServiceImpl @Inject constructor(
    @ActivityContext context: Context
) : PlaceSearchService {
    private val placesClient = Places.createClient(context)

    override suspend fun getSearchPlaces(keyword: String): List<PlaceSearchEntity> {
        val placeFields = listOf(
            Place.Field.DISPLAY_NAME,
            Place.Field.LOCATION,
            Place.Field.FORMATTED_ADDRESS
        )

        val searchByTextRequest = SearchByTextRequest
            .builder(keyword, placeFields)
            .build()

        return suspendCancellableCoroutine { continuation ->
            placesClient.searchByText(searchByTextRequest)
                .addOnSuccessListener { response ->
                    continuation.resume(response.places.map { it.toDomain() })
                }
                .addOnFailureListener { throwable ->
                    Timber.e(throwable.message)
                    continuation.resumeWithException(throwable)
                }
        }
    }
}