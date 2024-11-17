package com.capstone.lovemarker.feature.matching.sender

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SenderViewModel @Inject constructor(
//    private val matchingRepository: MatchingRepository
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<SenderSideEffect>()
    val sideEffect: SharedFlow<SenderSideEffect> = _sideEffect.asSharedFlow()

    fun postInvitationCode(anniversary: String) {

    }
}
