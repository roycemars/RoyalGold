package com.roycemars.royalgold.feature.market.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.roycemars.royalgold.feature.market.data.gemini.GeminiRepository
import com.roycemars.royalgold.feature.market.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RecommendationUiState {
    object Loading : RecommendationUiState()
    data class Success(val summary: String) : RecommendationUiState() // Assuming summarize returns a String
    data class Error(val message: String) : RecommendationUiState()
    object Empty : RecommendationUiState() // Optional: for when there's no recommendation yet or to show nothing
}

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

//    fun getRecommendations() {
//        Log.d(TAG, "getRecommendations - just calling")
//        viewModelScope.launch()
//        {
//            val recommendations = geminiRepository.summarize("")
//            Log.d(TAG, "recommendations: $recommendations")
//        }
//    }


    // 1. Create a private MutableStateFlow to hold the recommendation state
    private val _recommendationState =
        MutableStateFlow<RecommendationUiState>(RecommendationUiState.Empty) // Initial state
    // 2. Expose an immutable StateFlow for the UI to observe
    val recommendationState: StateFlow<RecommendationUiState> = _recommendationState.asStateFlow()

    fun getRecommendations() {
        Log.d(TAG, "getRecommendations - fetching...")
        viewModelScope.launch {
            _recommendationState.value = RecommendationUiState.Loading // Set loading state
            try {
                // Assuming geminiRepository.summarize("") is a suspend function returning a String
                // If it returns a Flow<String>, you'd collect it here.
                // Based on previous discussions, let's assume it returns a Flow<String>
                geminiRepository.summarize("") // This returns a Flow<String>
                    .catch { e -> // Handle errors from the flow
                        Log.e(TAG, "Error fetching recommendations", e)
                        _recommendationState.value = RecommendationUiState.Error("Failed to load recommendations: ${e.localizedMessage}")
                    }
                    .collect { summary -> // Collect the String from the Flow
                        Log.d(TAG, "Recommendations received: $summary")
                        if (summary.isNotBlank()) {
                            _recommendationState.value = RecommendationUiState.Success(summary)
                        } else {
                            _recommendationState.value = RecommendationUiState.Empty // Or some other appropriate state
                        }
                    }
            } catch (e: Exception) { // Catch errors from the suspend call itself if summarize wasn't a Flow
                Log.e(TAG, "Exception in getRecommendations", e)
                _recommendationState.value = RecommendationUiState.Error("Error: ${e.localizedMessage}")
            }
        }
    }

    // It's good practice to call this when the ViewModel is initialized
    // if you want recommendations to load immediately.
    init {
        getRecommendations()
    }


}