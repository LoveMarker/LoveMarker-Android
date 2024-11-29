package com.capstone.lovemarker.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.domain.detail.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<DetailSideEffect>()
    val sideEffect: SharedFlow<DetailSideEffect> = _sideEffect.asSharedFlow()

    fun getDetailInfo(memoryId: Int) {
        viewModelScope.launch {
            detailRepository.getDetail(memoryId = memoryId)
                .onSuccess { response ->
                    updateDetailState(detailModel = response.toModel())
                }.onFailure {
                    Timber.e(it.message)
                    _sideEffect.emit(DetailSideEffect.ShowErrorSnackbar(it))
                }
        }
    }

    private fun updateDetailState(detailModel: DetailModel) {
        _state.update {
            it.copy(detailModel = detailModel)
        }
    }
}
