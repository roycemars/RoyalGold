package com.roycemars.royalgold.ui.expenses.scanner

import android.content.Context
import java.io.File

interface ReceiptScanner {
    fun createPhotoFile(context: Context): File
}