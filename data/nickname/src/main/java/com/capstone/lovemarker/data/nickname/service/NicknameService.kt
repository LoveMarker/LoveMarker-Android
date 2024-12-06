package com.capstone.lovemarker.data.nickname.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.nickname.dto.NicknameRequest
import com.capstone.lovemarker.core.network.model.BaseResponseNothing
import com.capstone.lovemarker.data.nickname.dto.NicknameResponse
import retrofit2.http.Body
import retrofit2.http.PATCH

interface NicknameService {
    @PATCH("/api/user/nickname")
    suspend fun patchNickname(
        @Body nicknameRequest: NicknameRequest,
    ): BaseResponse<NicknameResponse>
}
