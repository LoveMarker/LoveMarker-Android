package com.capstone.lovemarker.data.detail.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.detail.dto.DetailResponse
import com.capstone.lovemarker.data.detail.service.DetailService
import javax.inject.Inject

class DetailDataSourceImpl @Inject constructor(
    private val detailService: DetailService
): DetailDataSource {
    override suspend fun getDetail(memoryId: Long): BaseResponse<DetailResponse> =
        detailService.getDetail(memoryId)
}
