package com.capstone.lovemarker.data.upload.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.upload.dto.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UploadDataSource {
    suspend fun postMemory(
        files: List<MultipartBody.Part>,
        otherMap: HashMap<String, RequestBody>
    ): BaseResponse<UploadResponse>
}
