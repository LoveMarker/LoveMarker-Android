package com.capstone.lovemarker.data.matching.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.core.network.model.BaseResponseNothing
import com.capstone.lovemarker.data.matching.dto.request.CoupleJoinRequest
import com.capstone.lovemarker.data.matching.dto.request.InvitationCodeRequest
import com.capstone.lovemarker.data.matching.dto.response.InvitationCodeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MatchingService {
    @POST("/api/couple")
    suspend fun postInvitationCode(
        @Body invitationCodeRequest: InvitationCodeRequest
    ): BaseResponse<InvitationCodeResponse>

    @POST("/api/couple/join")
    suspend fun postCouple(
        @Body coupleJoinRequest: CoupleJoinRequest
    ): BaseResponseNothing
}
