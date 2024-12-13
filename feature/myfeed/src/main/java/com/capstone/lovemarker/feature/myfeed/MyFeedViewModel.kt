package com.capstone.lovemarker.feature.myfeed

import androidx.lifecycle.ViewModel
import com.capstone.lovemarker.core.common.util.UiState
import com.capstone.lovemarker.domain.myfeed.repository.MyFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyFeedViewModel @Inject constructor(
    myFeedRepository: MyFeedRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MyFeedState())
    val state: StateFlow<MyFeedState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MyFeedSideEffect>()
    val sideEffect: SharedFlow<MyFeedSideEffect> = _sideEffect.asSharedFlow()

    val memories = myFeedRepository.getMemories()
        .catch {
            _sideEffect.emit(MyFeedSideEffect.ShowErrorSnackbar(it))
        }

    fun updateUiState(uiState: UiState<Unit>) {
        _state.update {
            it.copy(
                uiState = uiState
            )
        }
    }
}
