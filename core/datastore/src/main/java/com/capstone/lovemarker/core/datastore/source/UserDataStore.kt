package com.capstone.lovemarker.core.datastore.source

import com.capstone.lovemarker.core.datastore.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    val userData: Flow<UserData>
    suspend fun updateAccessToken(token: String)
    suspend fun updateRefreshToken(token: String)
    suspend fun updateAutoLogin(configured: Boolean)
    suspend fun updateNickname(nickname: String)
    suspend fun clear()
}
