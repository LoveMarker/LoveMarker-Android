package com.capstone.lovemarker.data.detail.repository

import com.capstone.lovemarker.data.detail.source.DetailDataSource
import com.capstone.lovemarker.domain.detail.entity.DetailEntity
import com.capstone.lovemarker.domain.detail.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailDataSource: DetailDataSource
) : DetailRepository {
    override suspend fun getDetail(memoryId: Int): Result<DetailEntity> = runCatching {
        detailDataSource.getDetail(memoryId.toLong()).data.toDomain()
    }
}
