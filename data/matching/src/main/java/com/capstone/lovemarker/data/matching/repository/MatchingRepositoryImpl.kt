package com.capstone.lovemarker.data.matching.repository

import com.capstone.lovemarker.data.matching.dto.InvitationCodeRequest
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
}
