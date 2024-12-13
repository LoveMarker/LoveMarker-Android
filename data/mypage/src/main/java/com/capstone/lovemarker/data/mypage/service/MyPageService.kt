package com.capstone.lovemarker.data.mypage.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.core.network.model.BaseResponseNothing
import com.capstone.lovemarker.data.mypage.dto.MyPageResponse
import retrofit2.http.DELETE
import retrofit2.http.GET

interface MyPageService {
    @GET("/api/user")
    suspend fun getMyPageInfo(): BaseResponse<MyPageResponse>

    @DELETE("/api/couple")
    suspend fun deleteCouple(): BaseResponseNothing
}
