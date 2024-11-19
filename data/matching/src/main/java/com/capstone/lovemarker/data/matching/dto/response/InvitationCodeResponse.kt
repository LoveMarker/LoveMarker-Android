package com.capstone.lovemarker.data.matching.dto.response

import com.capstone.lovemarker.domain.matching.entity.InvitationCodeEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvitationCodeResponse(
    @SerialName("invitationCode")
    val invitationCode: String
) {
    fun toDomain() = InvitationCodeEntity(
        invitationCode = invitationCode
    )
}
