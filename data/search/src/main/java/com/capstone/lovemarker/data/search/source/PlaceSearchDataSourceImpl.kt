package com.capstone.lovemarker.data.search.source

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchByTextRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PlaceSearchDataSourceImpl @Inject constructor(
    private val placesClient: PlacesClient,
) : PlaceSearchDataSource {
    override suspend fun getSearchPlaces(keyword: String): List<Place> {
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
                    continuation.resume(response.places)
                }
                .addOnFailureListener { throwable ->
                    continuation.resumeWithException(throwable)
                }
        }
    }
}
