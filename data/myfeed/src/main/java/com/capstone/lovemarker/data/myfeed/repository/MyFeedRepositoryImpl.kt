package com.capstone.lovemarker.data.myfeed.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.capstone.lovemarker.data.myfeed.service.MyFeedService
import com.capstone.lovemarker.data.myfeed.source.MyFeedPagingSource
import com.capstone.lovemarker.domain.myfeed.entity.MemoryEntity
import com.capstone.lovemarker.domain.myfeed.repository.MyFeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyFeedRepositoryImpl @Inject constructor(
    private val myFeedService: MyFeedService
) : MyFeedRepository {
    override fun getMemories(): Flow<PagingData<MemoryEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                prefetchDistance = PRE_FETCH_DISTANCE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MyFeedPagingSource(myFeedService) }
        ).flow

    companion object {
        private const val ITEMS_PER_PAGE = 10
        private const val PRE_FETCH_DISTANCE = 5
    }
}