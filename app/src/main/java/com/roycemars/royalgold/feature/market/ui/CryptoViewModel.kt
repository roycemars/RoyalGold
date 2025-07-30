package com.roycemars.royalgold.feature.market.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.roycemars.royalgold.feature.market.data.gemini.GeminiRepository
import com.roycemars.royalgold.feature.market.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
//    pager: Pager<Int, CryptoEntity>
    private val cryptoRepo: CryptoRepository,
    private val geminiRepository: GeminiRepository
): ViewModel() {

    private val TAG = "CryptoViewModel"

//    val cryptoPagingFlow = pager.flow.map { pagingData ->
//        pagingData.map { it.toCrypto() }
//    }
//        .cachedIn(viewModelScope)

    val cryptoFlow = cryptoRepo.getCrypto().cachedIn(viewModelScope)

    fun getRecommendations() {
        Log.d(TAG, "getRecommendations - just calling")
        viewModelScope.launch()
        {
            val recommendations = geminiRepository.summarize("")
            Log.d(TAG, "recommendations: $recommendations")
        }
    }

}