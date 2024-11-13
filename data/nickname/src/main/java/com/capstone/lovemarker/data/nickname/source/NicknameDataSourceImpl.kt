package com.capstone.lovemarker.data.nickname.source

import com.capstone.lovemarker.data.nickname.dto.NicknameRequest
import com.capstone.lovemarker.data.nickname.service.NicknameService
import javax.inject.Inject

class NicknameDataSourceImpl @Inject constructor(
    private val nicknameService: NicknameService,
) : NicknameDataSource {
    override suspend fun patchNickname(nicknameRequest: NicknameRequest) =
        nicknameService.patchNickname(
            nicknameRequest = nicknameRequest
        )
}
