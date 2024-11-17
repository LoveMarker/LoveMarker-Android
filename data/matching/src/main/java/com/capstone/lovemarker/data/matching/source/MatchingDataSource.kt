package com.capstone.lovemarker.data.matching.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.matching.dto.InvitationCodeRequest
import com.capstone.lovemarker.data.matching.dto.InvitationCodeResponse

interface MatchingDataSource {
    suspend fun postInvitationCode(
        invitationCodeRequest: InvitationCodeRequest
    ): BaseResponse<InvitationCodeResponse>
}
