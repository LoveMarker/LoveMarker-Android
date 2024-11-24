package com.capstone.lovemarker.feature.search

import com.capstone.lovemarker.core.common.util.UiState
import com.capstone.lovemarker.domain.search.entity.PlaceEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val keyword: String = "",
    val uiState: UiState<PersistentList<PlaceEntity>> = UiState.Empty
)
