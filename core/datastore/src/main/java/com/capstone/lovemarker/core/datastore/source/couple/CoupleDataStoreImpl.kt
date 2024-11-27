package com.capstone.lovemarker.core.datastore.source.couple

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.lovemarker.core.datastore.di.qualifier.Couple
import com.capstone.lovemarker.core.datastore.model.CoupleData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoupleDataStoreImpl @Inject constructor(
    @Couple private val dataStore: DataStore<Preferences>,
) : CoupleDataStore {
    object PreferencesKey {
        val CONNECTED = booleanPreferencesKey("CONNECTED")
        val ANNIVERSARY_DAYS = intPreferencesKey("ANNIVERSARY_DAYS")
        val PARTNER_NICKNAME = stringPreferencesKey("PARTNER_NICKNAME")
    }

    override val coupleData: Flow<CoupleData> = dataStore.data.map { preferences ->
        CoupleData(
            connected = preferences[PreferencesKey.CONNECTED] ?: false,
            anniversaryDays = preferences[PreferencesKey.ANNIVERSARY_DAYS] ?: 0,
            partnerNickname = preferences[PreferencesKey.PARTNER_NICKNAME] ?: "",
        )
    }

    override suspend fun updateConnectedState(connected: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.CONNECTED] = connected
        }
    }

    override suspend fun updateAnniversaryDays(days: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ANNIVERSARY_DAYS] = days
        }
    }

    override suspend fun updatePartnerNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.PARTNER_NICKNAME] = nickname
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}
