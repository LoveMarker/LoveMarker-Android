package com.capstone.lovemarker.data.myfeed.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.lovemarker.data.myfeed.dto.toDomain
import com.capstone.lovemarker.data.myfeed.service.MyFeedService
import com.capstone.lovemarker.domain.myfeed.entity.MemoryEntity
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MyFeedPagingSource @Inject constructor(
    private val myFeedService: MyFeedService
) : PagingSource<Int, MemoryEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MemoryEntity> {
        val pageKey = params.key ?: STARTING_KEY

        return try {
            val myFeedEntity = myFeedService.getMemories(
                pageIndex = pageKey,
                itemsPerPage = params.loadSize
            ).data.toDomain()

            LoadResult.Page(
                data = myFeedEntity.memories,
                prevKey = if (pageKey == STARTING_KEY) null else pageKey - 1,
                nextKey = if (myFeedEntity.memories.isEmpty() || !myFeedEntity.hasNextPage) null else pageKey + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MemoryEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_KEY = 0
    }
}