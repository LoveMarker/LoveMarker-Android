package com.capstone.lovemarker.feature.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.core.datastore.source.couple.CoupleDataStore
import com.capstone.lovemarker.domain.archive.entity.MemoryEntity
import com.capstone.lovemarker.domain.archive.repository.ArchiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
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
import timber.log.Timber
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

    val memories = archiveRepository.getMemories()
        .catch { throwable ->
            _sideEffect.emit(ArchiveSideEffect.ShowErrorSnackbar(throwable))
        }

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

    fun updateLoadPageState(isLoadPage: Boolean) {
        _state.update {
            it.copy(
                isLoadPage = isLoadPage
            )
        }
    }

    fun updateMemories(memories: PersistentList<MemoryEntity>) {
        _state.update {
            it.copy(memories = memories)
        }
    }

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
