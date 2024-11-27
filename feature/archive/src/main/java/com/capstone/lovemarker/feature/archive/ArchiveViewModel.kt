package com.capstone.lovemarker.feature.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.lovemarker.domain.archive.entity.MemoryEntity
import com.capstone.lovemarker.domain.archive.repository.ArchiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    archiveRepository: ArchiveRepository
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<ArchiveSideEffect>()
    val sideEffect: SharedFlow<ArchiveSideEffect> = _sideEffect.asSharedFlow()

    val memories: Flow<PagingData<MemoryEntity>> = archiveRepository.getMemories()
        .catch { throwable ->
            _sideEffect.emit(ArchiveSideEffect.ShowErrorSnackbar(throwable))
        }.cachedIn(viewModelScope)

    fun triggerNavigationEffect(memoryId: Int) {
        viewModelScope.launch {
            _sideEffect.emit(ArchiveSideEffect.NavigateToDetail(memoryId))
        }
    }
}
