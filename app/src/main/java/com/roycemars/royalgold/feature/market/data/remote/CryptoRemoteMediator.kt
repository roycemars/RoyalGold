package com.roycemars.royalgold.feature.market.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.roycemars.royalgold.feature.market.data.local.CryptoDatabase
import com.roycemars.royalgold.feature.market.data.local.CryptoEntity
import com.roycemars.royalgold.feature.market.data.mappers.toCryptoEntity
import okio.IOException

@OptIn(ExperimentalPagingApi::class)
class CryptoRemoteMediator(
    private val cryptoDb: CryptoDatabase,
    private val cryptoApi: CryptoApi
): RemoteMediator<Int, CryptoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CryptoEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val cryptoListings = cryptoApi.getListings(
                start = loadKey,
                limit = state.config.pageSize
            )

            cryptoDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    cryptoDb.dao.clearAll()
                }
                val cryptoEntities = cryptoListings.map {  it.toCryptoEntity() }
                cryptoDb.dao.upsertAll(cryptoEntities)
            }

            MediatorResult.Success(endOfPaginationReached = cryptoListings.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}