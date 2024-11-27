package com.capstone.lovemarker.core.datastore.source.user

import com.capstone.lovemarker.core.datastore.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    val user: Flow<User>
    suspend fun updateAccessToken(token: String)
    suspend fun updateRefreshToken(token: String)
    suspend fun updateAutoLogin(configured: Boolean)
    suspend fun updateNickname(nickname: String)
    suspend fun clear()
}
