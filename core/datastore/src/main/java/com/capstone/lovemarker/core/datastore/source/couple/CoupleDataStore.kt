package com.capstone.lovemarker.core.datastore.source.couple

import com.capstone.lovemarker.core.datastore.model.CoupleData
import kotlinx.coroutines.flow.Flow

interface CoupleDataStore {
    val coupleData: Flow<CoupleData>
    suspend fun updateCoupleConnectState(connected: Boolean)
    suspend fun clear()
}
