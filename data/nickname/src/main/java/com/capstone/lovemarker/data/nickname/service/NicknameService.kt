package com.capstone.lovemarker.data.nickname.service

import com.capstone.lovemarker.data.nickname.dto.NicknameRequest
import com.capstone.lovemarker.core.network.model.BaseResponseNothing
import retrofit2.http.Body
import retrofit2.http.PATCH

interface NicknameService {
    @PATCH("/api/user/nickname")
    suspend fun patchNickname(
        @Body nicknameRequest: NicknameRequest,
    ): BaseResponseNothing
}
