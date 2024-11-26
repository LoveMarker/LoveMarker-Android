package com.capstone.lovemarker.data.upload.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.upload.dto.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface UploadService {
    @Multipart
    @POST("/api/memory")
    suspend fun postMemory(
        @Part files: List<MultipartBody.Part>,
        @PartMap otherMap: HashMap<String, RequestBody>
    ): BaseResponse<UploadResponse>
}
