package com.capstone.lovemarker.domain.nickname.repository

import com.capstone.lovemarker.domain.nickname.entity.NicknameEntity

interface NicknameRepository {
    suspend fun patchNickname(nickname: String): Result<NicknameEntity>
}
