package com.capstone.lovemarker.data.archive.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.capstone.lovemarker.data.archive.service.ArchiveService
import com.capstone.lovemarker.data.archive.source.ArchivePagingSource
import com.capstone.lovemarker.domain.archive.entity.MemoryEntity
import com.capstone.lovemarker.domain.archive.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveRepositoryImpl @Inject constructor(
    private val archiveService: ArchiveService
): ArchiveRepository {
    override fun getMemories(): Flow<PagingData<MemoryEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PRE_FETCH_DISTANCE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArchivePagingSource(archiveService) }
        ).flow

    companion object {
        private const val ITEMS_PER_PAGE = 10
        private const val PRE_FETCH_DISTANCE = 5
    }
}
