package com.roycemars.royalgold.ui.scanner

import android.content.Context
import java.io.File

interface ReceiptScanner {
    fun createPhotoFile(context: Context): File
}