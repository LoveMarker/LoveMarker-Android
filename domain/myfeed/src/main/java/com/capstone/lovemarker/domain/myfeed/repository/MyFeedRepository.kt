package com.capstone.lovemarker.domain.myfeed.repository

import androidx.paging.PagingData
import com.capstone.lovemarker.domain.myfeed.entity.MemoryEntity
import kotlinx.coroutines.flow.Flow

interface MyFeedRepository {
    fun getMemories(): Flow<PagingData<MemoryEntity>>
}
