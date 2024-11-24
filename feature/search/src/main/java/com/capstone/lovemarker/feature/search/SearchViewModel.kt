package com.capstone.lovemarker.feature.search

import androidx.lifecycle.ViewModel
import com.capstone.lovemarker.core.common.util.UiState
import com.capstone.lovemarker.domain.search.entity.PlaceEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel: ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SearchSideEffect>()
    val sideEffect: SharedFlow<SearchSideEffect> = _sideEffect.asSharedFlow()

    fun updateSearchKeyword(keyword: String) {
        _state.update {
            it.copy(keyword = keyword)
        }
    }

    fun updateUiState(uiState: UiState<PersistentList<PlaceEntity>>) {
        _state.update {
            it.copy(
                uiState = uiState
            )
        }
    }
}
