package com.roycemars.royalgold.ui.expenses.scanner

import android.annotation.SuppressLint
import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

/**
 * TODO: Convert receipt image to database records with Gemini
 */

class ReceiptScannerImpl @Inject constructor() : ReceiptScanner {
    @SuppressLint("SimpleDateFormat")
    override fun createPhotoFile(context: Context): File {
        // ... (existing implementation)
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = context.externalCacheDir ?: context.cacheDir // Prefer external cache
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }
}