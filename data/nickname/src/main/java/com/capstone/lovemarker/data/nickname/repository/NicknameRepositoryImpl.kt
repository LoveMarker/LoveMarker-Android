package com.capstone.lovemarker.data.nickname.repository

import com.capstone.lovemarker.data.nickname.source.NicknameDataSource
import javax.inject.Inject

class NicknameRepositoryImpl @Inject constructor(
    private val nicknameDataSource: NicknameDataSource
) {

}