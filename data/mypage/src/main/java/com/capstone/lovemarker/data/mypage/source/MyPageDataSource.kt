package com.capstone.lovemarker.data.mypage.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.core.network.model.BaseResponseNothing
import com.capstone.lovemarker.data.mypage.dto.MyPageResponse

interface MyPageDataSource {
    suspend fun getCoupleInfo(): BaseResponse<MyPageResponse>
    suspend fun deleteCouple() : BaseResponseNothing
}
