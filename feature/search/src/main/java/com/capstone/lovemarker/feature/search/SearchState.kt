package com.capstone.lovemarker.feature.search

import com.capstone.lovemarker.core.common.util.UiState
import com.capstone.lovemarker.core.model.SearchPlace
import kotlinx.collections.immutable.PersistentList

data class SearchState(
    val keyword: String = "",
    val uiState: UiState<PersistentList<SearchPlace>> = UiState.Empty
)