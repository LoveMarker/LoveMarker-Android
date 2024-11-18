package com.capstone.lovemarker.data.mypage.service

import com.capstone.lovemarker.core.network.model.BaseResponseNothing
import retrofit2.http.DELETE

interface MyPageService {
    @DELETE("/api/couple")
    suspend fun deleteCouple(): BaseResponseNothing
}
