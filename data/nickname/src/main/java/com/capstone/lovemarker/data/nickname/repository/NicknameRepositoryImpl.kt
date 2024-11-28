package com.capstone.lovemarker.data.nickname.repository

import com.capstone.lovemarker.data.nickname.dto.NicknameRequest
import com.capstone.lovemarker.data.nickname.source.NicknameDataSource
import com.capstone.lovemarker.domain.nickname.entity.NicknameEntity
import com.capstone.lovemarker.domain.nickname.repository.NicknameRepository
import javax.inject.Inject

class NicknameRepositoryImpl @Inject constructor(
    private val nicknameDataSource: NicknameDataSource,
) : NicknameRepository {
    override suspend fun patchNickname(nickname: String): Result<NicknameEntity> = runCatching {
        nicknameDataSource.patchNickname(
            nicknameRequest = NicknameRequest(nickname)
        ).data.toDomain()
    }
}
