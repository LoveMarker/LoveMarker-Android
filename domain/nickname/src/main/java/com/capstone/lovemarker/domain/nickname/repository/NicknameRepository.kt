package com.capstone.lovemarker.domain.nickname.repository

interface NicknameRepository {
    suspend fun patchNickname(nickname: String): Result<Unit>
}
