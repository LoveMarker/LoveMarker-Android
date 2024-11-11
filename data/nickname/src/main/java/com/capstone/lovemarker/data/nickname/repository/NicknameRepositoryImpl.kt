package com.capstone.lovemarker.data.nickname.repository

import com.capstone.lovemarker.data.nickname.dto.NicknameRequest
import com.capstone.lovemarker.data.nickname.source.NicknameDataSource
import com.capstone.lovemarker.domain.nickname.repository.NicknameRepository
import javax.inject.Inject

class NicknameRepositoryImpl @Inject constructor(
    private val nicknameDataSource: NicknameDataSource,
) : NicknameRepository {
    override suspend fun patchNickname(nickname: String) = runCatching {
        nicknameDataSource.patchNickname(NicknameRequest(nickname)).data
    }
}
