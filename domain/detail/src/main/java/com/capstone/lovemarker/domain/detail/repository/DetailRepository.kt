package com.capstone.lovemarker.domain.detail.repository

import com.capstone.lovemarker.domain.detail.entity.DetailEntity

interface DetailRepository {
    suspend fun getDetail(memoryId: Int): Result<DetailEntity>
}
