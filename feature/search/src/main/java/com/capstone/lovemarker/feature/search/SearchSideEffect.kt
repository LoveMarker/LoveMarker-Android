package com.capstone.lovemarker.feature.search

import com.capstone.lovemarker.core.model.SearchPlace

sealed interface SearchSideEffect {
    // todo: 검색 목록에서 하나의 아이템을 클릭했을 때 트리거 되어야 한다.
    data class NavigateToContent(
        val place: SearchPlace
    ): SearchSideEffect

    data class ShowErrorSnackbar(
        val throwable: Throwable
    ): SearchSideEffect
}
