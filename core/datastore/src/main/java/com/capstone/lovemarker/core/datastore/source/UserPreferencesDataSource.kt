package com.capstone.lovemarker.core.datastore.source

import com.capstone.lovemarker.core.datastore.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataSource {
    val userData: Flow<UserData>
    suspend fun updateAccessToken(token: String)
    suspend fun updateRefreshToken(token: String)
    suspend fun updateAutoLogin(configured: Boolean)
    suspend fun clear()
}
