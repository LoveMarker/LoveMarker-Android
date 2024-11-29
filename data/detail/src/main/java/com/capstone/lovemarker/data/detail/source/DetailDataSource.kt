package com.capstone.lovemarker.data.detail.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.detail.dto.DetailResponse

interface DetailDataSource {
    suspend fun getDetail(memoryId: Long): BaseResponse<DetailResponse>
}
