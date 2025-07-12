package com.roycemars.royalgold.ui.screens // Adjust package name

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.roycemars.royalgold.ui.screens.expenses.ExpenseListItem
import com.roycemars.royalgold.ui.screens.expenses.ExpensesScreenViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen(
    viewModel: ExpensesScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    var isCapturing by remember { mutableStateOf(false) }
    var captureError by remember { mutableStateOf<String?>(null) }
    var lastSavedImageUri by remember { mutableStateOf<Uri?>(null) } // This will control list visibility
    val imageCaptureUseCase = remember { ImageCapture.Builder().build() }
    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }

    val expenseItemsList = remember { viewModel.expenseItemsList }

    LaunchedEffect(key1 = cameraPermissionState.status) {
        if (cameraPermissionState.status.isGranted) { // Only if granted
            val providerFuture = ProcessCameraProvider.getInstance(context)
            providerFuture.addListener({
                cameraProvider = providerFuture.get()
            }, ContextCompat.getMainExecutor(context))
        } else {
            cameraProvider = null // Explicitly nullify if permission revoked/not granted
        }
    }

    // Condition 3: Hide list completely during taking picture (isCapturing = true)
    // Condition 1: Show full screen, scrollable list after picture is successfully taken (lastSavedImageUri != null)
    // Condition 2: Show half screen scrollable list below 100dp static card with button to request permissions
    //              (cameraPermissionState.status.isGranted is false AND lastSavedImageUri is null AND !isCapturing)

    if (lastSavedImageUri != null && !isCapturing) {
        // --- Condition 1: Full screen list after successful capture ---
        Column(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) { // Added padding at top
            Text(
                "Receipt Scanned!", // Confirmation message
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            )
            // You could show the image URI here too if needed
            // Text("Image saved at: $lastSavedImageUri", modifier = Modifier.padding(horizontal = 16.dp))
            // Spacer(modifier = Modifier.height(8.dp))
            Button( // Button to go back or clear the image state
                onClick = { lastSavedImageUri = null /* Reset to allow re-scan or go back */ },
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            ) {
                Text("Scan Receipt")
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(expenseItemsList) { wallet ->
                    ExpenseListItem(item = wallet)
                }
            }
        }
    } else if (isCapturing) {
        // --- Condition 3: UI during capture (List is hidden) ---
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
            Text("Saving image...", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 70.dp))
        }
    } else if (!cameraPermissionState.status.isGranted) {
        // --- Condition 2: Permission not granted, show half list ---
        Column(modifier = Modifier.fillMaxSize()) {
            Card( // Static card for permission request
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp) // Adjusted height slightly
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                        "Camera permission is important for scanning receipts. Please grant the permission."
                    } else {
                        "Camera permission is required to scan receipts."
                    }
                    Text(textToShow, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text("Grant Permission")
                    }
                }
            }
            // Half screen scrollable list
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes remaining space
            ) {
                items(expenseItemsList) { wallet ->
                    ExpenseListItem(item = wallet)
                }
            }
        }
    } else {
        // --- Default: Camera preview is visible, ready to capture ---
        // (This is when permission is granted, not capturing, and no image is yet saved)
        Box(modifier = Modifier.fillMaxSize()) {
            val previewView = remember { PreviewView(context) }
            LaunchedEffect(cameraProvider, lifecycleOwner) { /* ... existing camera binding ... */
                cameraProvider?.let { provider ->
                    try {
                        provider.unbindAll()
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                        provider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCaptureUseCase
                        )
                    } catch (exc: Exception) {
                        Log.e("ScanScreen", "Use case binding failed", exc)
                        captureError = "Failed to initialize camera: ${exc.localizedMessage}"
                    }
                }
            }
            AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                if (captureError != null) {
                    Text("Error: $captureError", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // No lastSavedImageUri text here if we are in this state

                Button(
                    onClick = {
                        isCapturing = true // Set capturing to true
                        captureError = null
                        // lastSavedImageUri = null // Already null here

                        val photoFile = createPhotoFile(context)
                        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                        imageCaptureUseCase.takePicture(
                            outputOptions,
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                    isCapturing = false // Reset capturing
                                    lastSavedImageUri = output.savedUri // This triggers UI change
                                    Log.d("ScanScreen", "Image saved: ${output.savedUri}")
                                }

                                override fun onError(exc: ImageCaptureException) {
                                    isCapturing = false // Reset capturing
                                    captureError = exc.message ?: "Image capture failed"
                                    Log.e("ScanScreen", "Image capture failed", exc)
                                }
                            }
                        )
                    },
                    enabled = cameraProvider != null, // Button enabled when camera is ready
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Scan Receipt")
                }
            }
        }
    }

    // Clean up camera resources (no change here)
    DisposableEffect(Unit) {
        onDispose {
            cameraProvider?.unbindAll()
        }
    }
}

// createPhotoFile function (no change here)
@SuppressLint("SimpleDateFormat")
fun createPhotoFile(context: Context): File {
    // ... (existing implementation)
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir = context.externalCacheDir ?: context.cacheDir // Prefer external cache
    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}