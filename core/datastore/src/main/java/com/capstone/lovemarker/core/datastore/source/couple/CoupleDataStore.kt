package com.capstone.lovemarker.core.datastore.source.couple

import com.capstone.lovemarker.core.datastore.model.CoupleData
import kotlinx.coroutines.flow.Flow

interface CoupleDataStore {
    val coupleData: Flow<CoupleData>
    suspend fun updateConnectedState(connected: Boolean)
    suspend fun updateAnniversaryDays(days: Int)
    suspend fun updatePartnerNickname(nickname: String)
    suspend fun clear()
}
