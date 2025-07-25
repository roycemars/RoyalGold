package com.roycemars.royalgold.feature.market.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [CryptoEntity::class],
    version = 2
)
abstract class CryptoDatabase: RoomDatabase() {
    abstract val dao: CryptoDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE CryptoEntity ADD COLUMN percentChange1h REAL")
                db.execSQL("ALTER TABLE CryptoEntity ADD COLUMN percentChange24h REAL")
                db.execSQL("ALTER TABLE CryptoEntity ADD COLUMN percentChange7d REAL")
                db.execSQL("ALTER TABLE CryptoEntity ADD COLUMN percentChange30d REAL")
            }
        }
    }
}