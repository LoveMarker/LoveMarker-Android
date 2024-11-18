package com.capstone.lovemarker.feature.matching.sender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.domain.matching.repository.MatchingRepository
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
class SenderViewModel @Inject constructor(
    private val matchingRepository: MatchingRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SenderState())
    val state: StateFlow<SenderState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SenderSideEffect>()
    val sideEffect: SharedFlow<SenderSideEffect> = _sideEffect.asSharedFlow()

    fun updateAnniversary(anniversary: String) {
        _state.update {
            it.copy(anniversary = anniversary)
        }
    }

    fun updateButtonEnabled(enabled: Boolean) {
        _state.update {
            it.copy(buttonEnabled = enabled)
        }
    }

    fun updateInvitationCode(invitationCode: String) {
        _state.update {
            it.copy(invitationCode = invitationCode)
        }
    }

    fun updateDialogState(showDialog: Boolean) {
        _state.update {
            it.copy(showDialog = showDialog)
        }
    }

    fun postInvitationCode(anniversary: String) {
        viewModelScope.launch {
            matchingRepository.postInvitationCode(anniversary)
                .onSuccess { response ->
                    Timber.d(response.invitationCode)
                    _sideEffect.emit(SenderSideEffect.ShowShareDialog(response.invitationCode))
                }.onFailure {
                    _sideEffect.emit(SenderSideEffect.ShowErrorSnackbar(it))
                }
        }
    }
}