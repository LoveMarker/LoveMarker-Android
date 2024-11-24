package com.capstone.lovemarker.feature.search

import com.capstone.lovemarker.domain.search.entity.PlaceEntity

sealed interface SearchSideEffect {
    // todo: 검색 목록에서 하나의 아이템을 클릭했을 때 트리거 되어야 한다.
    data class NavigateToContent(
        val placeEntity: PlaceEntity
    ): SearchSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ): SearchSideEffect
}
