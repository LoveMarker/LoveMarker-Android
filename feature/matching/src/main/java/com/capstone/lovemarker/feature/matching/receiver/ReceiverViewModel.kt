package com.capstone.lovemarker.feature.matching.receiver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.domain.matching.repository.MatchingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiverViewModel @Inject constructor(
    private val matchingRepository: MatchingRepository
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<ReceiverSideEffect>()
    val sideEffect: SharedFlow<ReceiverSideEffect> = _sideEffect.asSharedFlow()

    fun postCouple(invitationCode: String) {
        viewModelScope.launch {
            matchingRepository.postCouple(invitationCode)
                .onSuccess {
                    _sideEffect.emit(ReceiverSideEffect.NavigateToMap)
                }.onFailure {
                    _sideEffect.emit(ReceiverSideEffect.ShowErrorSnackbar(it))
                }
        }
    }
}
