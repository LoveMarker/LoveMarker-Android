package com.capstone.lovemarker.data.nickname.source

import com.capstone.lovemarker.data.nickname.dto.NicknameRequest
import com.capstone.lovemarker.core.network.model.BaseResponseNothing
import com.capstone.lovemarker.data.nickname.dto.NicknameResponse

interface NicknameDataSource {
    suspend fun patchNickname(nicknameRequest: NicknameRequest): NicknameResponse
}