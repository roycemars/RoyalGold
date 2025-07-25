package com.roycemars.royalgold.feature.market.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.roycemars.royalgold.core.util.TimeConverter
import com.roycemars.royalgold.feature.market.data.local.CryptoEntity
import com.roycemars.royalgold.feature.market.data.remote.CryptoDto
import com.roycemars.royalgold.feature.market.domain.Crypto

@RequiresApi(Build.VERSION_CODES.O)
fun CryptoDto.toCryptoEntity(): CryptoEntity {
    return CryptoEntity(
        id = id,
        name = name,
        symbol = symbol,
        price = quote?.usd?.price ?: 0.0,
        percentChange1h = quote?.usd?.percentChange1h ?: 0.0,
        percentChange24h = quote?.usd?.percentChange24h ?: 0.0,
        percentChange7d = quote?.usd?.percentChange7d ?: 0.0,
        percentChange30d = quote?.usd?.percentChange30d ?: 0.0,
        lastUpdated = TimeConverter.isoStringToLong(lastUpdated) ?: 0
    )
}

fun CryptoEntity.toCrypto(): Crypto {
    return Crypto(
        id = id,
        name = name,
        symbol = symbol,
        price = price,
        percentChange1h = percentChange1h ?: 0.0,
        percentChange24h = percentChange24h ?: 0.0,
        percentChange7d = percentChange7d ?: 0.0,
        percentChange30d = percentChange30d ?: 0.0,
        lastUpdated = lastUpdated
    )
}