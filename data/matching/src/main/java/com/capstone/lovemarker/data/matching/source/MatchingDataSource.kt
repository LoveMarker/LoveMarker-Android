package com.capstone.lovemarker.data.matching.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.core.network.model.BaseResponseNothing
import com.capstone.lovemarker.data.matching.dto.request.CoupleJoinRequest
import com.capstone.lovemarker.data.matching.dto.request.InvitationCodeRequest
import com.capstone.lovemarker.data.matching.dto.response.InvitationCodeResponse

interface MatchingDataSource {
    suspend fun postInvitationCode(
        invitationCodeRequest: InvitationCodeRequest
    ): BaseResponse<InvitationCodeResponse>

    suspend fun postCouple(
        coupleJoinRequest: CoupleJoinRequest
    ): BaseResponseNothing
}
