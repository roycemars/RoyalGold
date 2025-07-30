package com.roycemars.royalgold.feature.market.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.roycemars.royalgold.feature.market.data.local.CryptoDatabase
import com.roycemars.royalgold.feature.market.data.local.CryptoEntity
import com.roycemars.royalgold.feature.market.data.remote.CryptoApi
import com.roycemars.royalgold.feature.market.data.remote.CryptoRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val db: CryptoDatabase,
    private val api: CryptoApi

): CryptoRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getCrypto(): Flow<PagingData<CryptoEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CryptoRemoteMediator(db, api),
            pagingSourceFactory = {
                db.dao.pagingSource()
            }
        ).flow
    }
}
