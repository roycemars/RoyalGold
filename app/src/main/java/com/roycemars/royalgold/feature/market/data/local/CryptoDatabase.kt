package com.roycemars.royalgold.feature.market.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CryptoEntity::class],
    version = 1
)
abstract class CryptoDatabase: RoomDatabase() {
    abstract val dao: CryptoDao
}