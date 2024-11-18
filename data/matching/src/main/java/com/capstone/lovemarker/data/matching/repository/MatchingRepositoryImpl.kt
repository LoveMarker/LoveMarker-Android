package com.capstone.lovemarker.data.matching.repository

import com.capstone.lovemarker.data.matching.dto.request.CoupleJoinRequest
import com.capstone.lovemarker.data.matching.dto.request.InvitationCodeRequest
import com.capstone.lovemarker.data.matching.source.MatchingDataSource
import com.capstone.lovemarker.domain.matching.repository.MatchingRepository
import javax.inject.Inject

class MatchingRepositoryImpl @Inject constructor(
    private val matchingDataSource: MatchingDataSource,
) : MatchingRepository {
    override suspend fun postInvitationCode(anniversary: String) = runCatching {
        matchingDataSource.postInvitationCode(
            invitationCodeRequest = InvitationCodeRequest(anniversary)
        ).data.toDomain()
    }

    override suspend fun postCouple(invitationCode: String): Result<Unit> = runCatching {
        matchingDataSource.postCouple(
            coupleJoinRequest = CoupleJoinRequest(invitationCode)
        )
    }
}
