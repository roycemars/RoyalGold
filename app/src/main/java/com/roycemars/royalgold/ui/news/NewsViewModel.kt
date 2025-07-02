package com.roycemars.royalgold.ui.news

import com.roycemars.royalgold.data.network.news.RetrofitClient
import com.roycemars.royalgold.data.news.CmcNewsArticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val articles: List<CmcNewsArticle>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val cmcApiService = RetrofitClient.cmcApiService
    private val apiKey = RetrofitClient.getApiKey() // Get the API key

    init {
        fetchLatestNews()
    }

    fun fetchLatestNews() {
        _uiState.value = NewsUiState.Loading
        viewModelScope.launch {
            if (apiKey == "MISSING_API_KEY" || apiKey == "YOUR_CMC_API_KEY_GOES_HERE_SECURELY") {
                _uiState.value = NewsUiState.Error("API Key not configured. Please set it up.")
                Log.e("NewsViewModel", "API Key is missing or is a placeholder.")
                return@launch
            }
            try {
                val response = cmcApiService.getLatestNews(apiKey = apiKey, limit = 30) // Fetch more for better scroll
                if (response.isSuccessful) {
                    val articles = response.body()?.data?.posts ?: emptyList()
                    _uiState.value = NewsUiState.Success(articles)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _uiState.value = NewsUiState.Error("API Error: ${response.code()} - $errorBody")
                    Log.e("NewsViewModel", "API Error: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error("Network Error: ${e.message}")
                Log.e("NewsViewModel", "Network Error: ${e.localizedMessage}", e)
            }
        }
    }
}