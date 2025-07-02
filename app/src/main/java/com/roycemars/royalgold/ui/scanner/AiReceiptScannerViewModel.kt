package com.roycemars.royalgold.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roycemars.royalgold.data.scanner.AiReceiptScanner
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File

class AiReceiptScannerViewModel : ViewModel() {
    private val scanner = AiReceiptScanner()

    fun processReceiptImage(imageFile: File) {
        viewModelScope.launch {
            val resultJson: JSONObject = scanner.callGeminiAPI(imageFile)
            // Process the resultJson
            // e.g., update LiveData or StateFlow to show in UI
            if (resultJson.length() > 0) {
                // Successfully got some data
            } else {
                // Handle error or empty response
            }
        }
    }
}