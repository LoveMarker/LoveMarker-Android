package com.capstone.lovemarker.data.matching.source

import com.capstone.lovemarker.data.matching.dto.InvitationCodeRequest
import com.capstone.lovemarker.data.matching.service.MatchingService
import javax.inject.Inject

class MatchingDataSourceImpl @Inject constructor(
    private val matchingService: MatchingService,
) : MatchingDataSource {
    override suspend fun postInvitationCode(invitationCodeRequest: InvitationCodeRequest) =
        matchingService.postInvitationCode(invitationCodeRequest)
}
