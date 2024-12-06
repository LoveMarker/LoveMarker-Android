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
    }

    override val coupleData: Flow<CoupleData> = dataStore.data.map { preferences ->
        CoupleData(
            connected = preferences[PreferencesKey.CONNECTED] ?: false,
        )
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
