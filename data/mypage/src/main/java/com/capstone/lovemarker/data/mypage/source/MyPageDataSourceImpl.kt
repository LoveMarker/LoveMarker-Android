package com.capstone.lovemarker.data.mypage.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.core.network.model.BaseResponseNothing
import com.capstone.lovemarker.data.mypage.dto.MyPageResponse
import com.capstone.lovemarker.data.mypage.service.MyPageService
import javax.inject.Inject

class MyPageDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService,
) : MyPageDataSource {
    override suspend fun getMyPageInfo(): BaseResponse<MyPageResponse> =
        myPageService.getMyPageInfo()

    override suspend fun deleteCouple(): BaseResponseNothing =
        myPageService.deleteCouple()
}
