package com.roycemars.royalgold.feature.market.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.roycemars.royalgold.feature.market.data.local.CryptoEntity
import com.roycemars.royalgold.feature.market.data.mappers.toCrypto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    pager: Pager<Int, CryptoEntity>
): ViewModel() {
    val cryptoPagingFlow = pager.flow.map { pagingData ->
        pagingData.map { it.toCrypto() }
    }
        .cachedIn(viewModelScope)
}