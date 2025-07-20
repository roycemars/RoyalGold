package com.roycemars.royalgold.feature.expenses.camera

import android.content.Context
import java.io.File

interface ReceiptScanner {
    fun createPhotoFile(context: Context): File
}