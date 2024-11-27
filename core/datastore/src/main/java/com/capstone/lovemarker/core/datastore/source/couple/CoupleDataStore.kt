package com.capstone.lovemarker.core.datastore.source.couple

import com.capstone.lovemarker.core.datastore.model.Couple
import kotlinx.coroutines.flow.Flow

interface CoupleDataStore {
    val couple: Flow<Couple>
    suspend fun updatePartnerNickname(nickname: String)
    suspend fun updateConnectedState(connected: Boolean)
    suspend fun clear()
}
