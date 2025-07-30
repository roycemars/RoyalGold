package com.roycemars.royalgold.feature.market.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.roycemars.royalgold.feature.market.data.local.CryptoDatabase
import com.roycemars.royalgold.feature.market.data.local.CryptoEntity
import com.roycemars.royalgold.feature.market.data.mappers.toCryptoEntity

@OptIn(ExperimentalPagingApi::class)
class CryptoRemoteMediator(
    private val db: CryptoDatabase,
    private val api: CryptoApi
): RemoteMediator<Int, CryptoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CryptoEntity>
    ): MediatorResult {
        return try {
            if (loadType != LoadType.REFRESH) return MediatorResult.Success(endOfPaginationReached = true)

            val responseDto = api.getListings(limit = 100)
            val entities = responseDto.data.map { it.toCryptoEntity() }

            db.withTransaction {
                db.dao.clearAll()
                db.dao.upsertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}