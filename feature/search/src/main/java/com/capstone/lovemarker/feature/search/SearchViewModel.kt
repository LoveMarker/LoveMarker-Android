package com.capstone.lovemarker.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.common.util.UiState
import com.capstone.lovemarker.feature.search.model.PlaceSearchModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SearchSideEffect>()
    val sideEffect: SharedFlow<SearchSideEffect> = _sideEffect.asSharedFlow()

    fun updateSearchKeyword(keyword: String) {
        _state.update {
            it.copy(keyword = keyword)
        }
    }

    fun updateUiState(uiState: UiState<PersistentList<PlaceSearchModel>>) {
        _state.update {
            it.copy(
                uiState = uiState
            )
        }
    }

    fun triggerContentNavigationEffect(place: PlaceSearchModel) {
        viewModelScope.launch {
            _sideEffect.emit(
                SearchSideEffect.NavigateToContent(
                    place = place
                )
            )
        }
    }
}
