package com.capstone.lovemarker.domain.upload.repository

import com.capstone.lovemarker.domain.upload.entity.UploadRequestEntity
import com.capstone.lovemarker.domain.upload.entity.UploadResponseEntity

interface UploadRepository {
    suspend fun postMemory(
        images: List<String>,
        requestEntity: UploadRequestEntity
    ): Result<UploadResponseEntity>
}
