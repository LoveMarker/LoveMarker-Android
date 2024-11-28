package com.capstone.lovemarker.feature.matching.receiver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStore
import com.capstone.lovemarker.domain.matching.repository.MatchingRepository
import com.capstone.lovemarker.domain.mypage.repository.MyPageRepository
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
class ReceiverViewModel @Inject constructor(
    private val matchingRepository: MatchingRepository,
    private val myPageRepository: MyPageRepository
    private val coupleDataStore: CoupleDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(ReceiverState())
    val state: StateFlow<ReceiverState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ReceiverSideEffect>()
    val sideEffect: SharedFlow<ReceiverSideEffect> = _sideEffect.asSharedFlow()

    fun updateInvitationCode(code: String) {
        _state.update {
            it.copy(invitationCode = code)
        }
    }

    fun postCouple(invitationCode: String) {
        viewModelScope.launch {
            myPageRepository.deleteCouple()

            matchingRepository.postCouple(invitationCode)
                .onSuccess {
                    coupleDataStore.updateConnectedState(connected = true)
                    _sideEffect.emit(ReceiverSideEffect.NavigateToMap)
                }.onFailure {
                    Timber.e(it.message)
                    _sideEffect.emit(ReceiverSideEffect.ShowErrorSnackbar(it))
                }
        }
    }
}
