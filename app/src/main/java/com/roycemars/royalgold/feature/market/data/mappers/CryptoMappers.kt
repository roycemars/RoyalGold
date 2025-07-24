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
        price = price,
        lastUpdated = TimeConverter.isoStringToLong(last_updated) ?: 0
    )
}

fun CryptoEntity.toCrypto(): Crypto {
    return Crypto(
        id = id,
        name = name,
        symbol = symbol,
        price = price,
        lastUpdated = lastUpdated
    )
}