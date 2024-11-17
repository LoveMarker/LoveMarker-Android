package com.capstone.lovemarker.data.nickname.source

import com.capstone.lovemarker.data.nickname.dto.NicknameRequest
import com.capstone.lovemarker.core.network.model.BaseResponseNothing

interface NicknameDataSource {
    suspend fun patchNickname(nicknameRequest: NicknameRequest): BaseResponseNothing
}