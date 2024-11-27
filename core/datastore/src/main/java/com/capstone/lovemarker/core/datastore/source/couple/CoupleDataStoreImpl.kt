package com.capstone.lovemarker.core.datastore.source.couple

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.lovemarker.core.datastore.model.Couple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoupleDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : CoupleDataStore {
    object PreferencesKey {
        val PARTNER_NICKNAME = stringPreferencesKey("PARTNER_NICKNAME")
        val CONNECTED = booleanPreferencesKey("CONNECTED")
    }

    override val couple: Flow<Couple> = dataStore.data.map { preferences ->
        Couple(
            partnerNickname = preferences[PreferencesKey.PARTNER_NICKNAME] ?: "",
            connected = preferences[PreferencesKey.CONNECTED] ?: false,
        )
    }

    override suspend fun updatePartnerNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.PARTNER_NICKNAME] = nickname
        }
    }

    override suspend fun updateConnectedState(connected: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.CONNECTED] = connected
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}
