package com.capstone.lovemarker.data.nickname.source

import com.capstone.lovemarker.core.network.model.BaseResponse
import com.capstone.lovemarker.data.nickname.dto.NicknameRequest

interface NicknameDataSource {
    suspend fun patchNickname(nicknameRequest: NicknameRequest): BaseResponse<Unit>
}