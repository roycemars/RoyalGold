package com.roycemars.royalgold.feature.market.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CryptoDao {
    @Upsert
    suspend fun upsertAll(crypto: List<CryptoEntity>)

    @Query("SELECT * FROM CryptoEntity")
    fun pagingSource(): PagingSource<Int, CryptoEntity>

    @Query("DELETE FROM CryptoEntity")
    suspend fun clearAll()
}