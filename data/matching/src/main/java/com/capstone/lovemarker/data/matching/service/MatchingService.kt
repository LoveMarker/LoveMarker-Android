package com.capstone.lovemarker.data.matching.service

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.matching.dto.InvitationCodeRequest
import com.capstone.lovemarker.data.matching.dto.InvitationCodeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MatchingService {
    @POST("/api/couple")
    suspend fun postInvitationCode(
        @Body invitationCodeRequest: InvitationCodeRequest
    ): BaseResponse<InvitationCodeResponse>
}
