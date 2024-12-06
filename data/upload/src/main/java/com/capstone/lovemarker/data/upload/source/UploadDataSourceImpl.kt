package com.capstone.lovemarker.data.upload.source

import com.capstone.lovemarker.data.upload.service.UploadService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UploadDataSourceImpl @Inject constructor(
    private val uploadService: UploadService
) : UploadDataSource {
    override suspend fun postMemory(
        files: List<MultipartBody.Part>,
        otherMap: HashMap<String, RequestBody>
    ) = uploadService.postMemory(files = files, otherMap = otherMap)
}
