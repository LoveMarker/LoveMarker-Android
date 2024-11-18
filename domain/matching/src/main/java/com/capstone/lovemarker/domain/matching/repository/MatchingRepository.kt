package com.capstone.lovemarker.domain.matching.repository

import com.capstone.lovemarker.domain.matching.entity.InvitationCodeEntity

interface MatchingRepository {
    suspend fun postInvitationCode(anniversary: String): Result<InvitationCodeEntity>
    suspend fun postCouple(invitationCode: String): Result<Unit>
}
