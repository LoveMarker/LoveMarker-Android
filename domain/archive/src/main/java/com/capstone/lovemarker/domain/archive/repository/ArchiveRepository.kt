package com.capstone.lovemarker.domain.archive.repository

import androidx.paging.PagingData
import com.capstone.lovemarker.domain.archive.entity.MemoryEntity
import kotlinx.coroutines.flow.Flow

interface ArchiveRepository {
    fun getMemories(): Flow<PagingData<MemoryEntity>>
}
