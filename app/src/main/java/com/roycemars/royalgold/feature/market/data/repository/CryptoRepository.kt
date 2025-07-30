package com.roycemars.royalgold.feature.market.data.repository

import androidx.paging.PagingData
import com.roycemars.royalgold.feature.market.data.local.CryptoEntity
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun getCrypto(): Flow<PagingData<CryptoEntity>>
}