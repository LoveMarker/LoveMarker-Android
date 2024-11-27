package com.capstone.lovemarker.core.datastore.source.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.lovemarker.core.datastore.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : UserDataStore {
    object PreferencesKey {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val AUTO_LOGIN = booleanPreferencesKey("AUTO_LOGIN")
        val NICKNAME = stringPreferencesKey("NICKNAME")
    }

    override val user: Flow<User> = dataStore.data.map { preferences ->
        User(
            accessToken = preferences[PreferencesKey.ACCESS_TOKEN] ?: "",
            refreshToken = preferences[PreferencesKey.REFRESH_TOKEN] ?: "",
            autoLoginConfigured = preferences[PreferencesKey.AUTO_LOGIN] ?: false,
            nickname = preferences[PreferencesKey.NICKNAME] ?: ""
        )
    }

    override suspend fun updateAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCESS_TOKEN] = token
        }
    }

    override suspend fun updateRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.REFRESH_TOKEN] = token
        }
    }

    override suspend fun updateAutoLogin(configured: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.AUTO_LOGIN] = configured
        }
    }

    override suspend fun updateNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.NICKNAME] = nickname
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}