package com.capstone.lovemarker.feature.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStore
import com.capstone.lovemarker.domain.archive.entity.MemoryEntity
import com.capstone.lovemarker.domain.archive.repository.ArchiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    archiveRepository: ArchiveRepository,
    private val coupleDataStore: CoupleDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(ArchiveState())
    val state: StateFlow<ArchiveState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ArchiveSideEffect>()
    val sideEffect: SharedFlow<ArchiveSideEffect> = _sideEffect.asSharedFlow()

    fun getCoupleConnectState(): Deferred<Boolean> {
        return viewModelScope.async {
            coupleDataStore.coupleData.first().connected
        }
    }

    fun updateCoupleConnectState(connected: Boolean) {
        _state.update {
            it.copy(coupleConnected = connected)
        }
    }

    fun updateMatchingDialogState(showDialog: Boolean) {
        _state.update {
            it.copy(
                showMatchingDialog = showDialog
            )
        }
    }

    val memories: Flow<PagingData<MemoryEntity>> = archiveRepository.getMemories()
        .catch { throwable ->
            _sideEffect.emit(ArchiveSideEffect.ShowErrorSnackbar(throwable))
        }.cachedIn(viewModelScope)

    fun triggerDetailNavigationEffect(memoryId: Int) {
        viewModelScope.launch {
            _sideEffect.emit(ArchiveSideEffect.NavigateToDetail(memoryId))
        }
    }

    fun triggerMatchingNavigationEffect() {
        viewModelScope.launch {
            _sideEffect.emit(ArchiveSideEffect.NavigateToMatching)
        }
    }
}
