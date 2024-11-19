package com.capstone.lovemarker.data.mypage.source

import com.capstone.lovemarker.core.network.model.BaseResponseNothing

interface MyPageDataSource {
    suspend fun deleteCouple() : BaseResponseNothing
}
