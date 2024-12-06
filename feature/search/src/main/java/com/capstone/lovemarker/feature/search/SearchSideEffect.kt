package com.capstone.lovemarker.feature.search

import com.capstone.lovemarker.feature.search.model.PlaceSearchModel

sealed interface SearchSideEffect {
    data class NavigateToContent(
        val place: PlaceSearchModel
    ): SearchSideEffect
}
