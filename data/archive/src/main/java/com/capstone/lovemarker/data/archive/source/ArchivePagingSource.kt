package com.capstone.lovemarker.data.archive.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.lovemarker.data.archive.dto.response.toDomain
import com.capstone.lovemarker.data.archive.service.ArchiveService
import com.capstone.lovemarker.domain.archive.entity.Memory
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ArchivePagingSource @Inject constructor(
    private val archiveService: ArchiveService
) : PagingSource<Int, Memory>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Memory> {
        val pageKey = params.key ?: STARTING_KEY

        return try {
            // 초기에 30개 로드, 이후에 10개씩 추가
            Timber.d("pageIndex: $pageKey, loadSize: ${params.loadSize}")
            val archiveEntity = archiveService.getMemories(
                pageIndex = pageKey,
                itemsPerPage = params.loadSize
            ).data.toDomain()

            LoadResult.Page(
                data = archiveEntity.memories,
                prevKey = if (pageKey == STARTING_KEY) null else pageKey - 1,
                nextKey = if (archiveEntity.memories.isEmpty() || !archiveEntity.hasNextPage) null else pageKey + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Memory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_KEY = 0
    }
}