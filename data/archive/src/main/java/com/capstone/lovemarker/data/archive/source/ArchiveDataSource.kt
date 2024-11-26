package com.capstone.lovemarker.data.archive.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.archive.dto.response.ArchiveResponse

interface ArchiveDataSource {
    suspend fun getMemories(page: Int, size: Int): BaseResponse<ArchiveResponse>
}