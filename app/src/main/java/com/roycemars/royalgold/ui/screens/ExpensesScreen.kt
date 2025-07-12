package com.roycemars.royalgold.ui.screens // Adjust package name

import android.Manifest
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
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

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    viewModel: ExpensesScreenViewModel = hiltViewModel()
) {
    val receiptScanner = viewModel.receiptScannerImpl
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
        if (cameraPermissionState.status.isGranted) {
            val providerFuture = ProcessCameraProvider.getInstance(context)
            providerFuture.addListener({
                cameraProvider = providerFuture.get()
            }, ContextCompat.getMainExecutor(context))
        } else {
            cameraProvider = null
        }
    }

    // Condition 3: Hide list completely during taking picture (isCapturing = true)
    // Condition 1: Show full screen, scrollable list after picture is successfully taken (lastSavedImageUri != null)
    // Condition 2: Show half screen scrollable list below 100dp static card with button to request permissions
    //              (cameraPermissionState.status.isGranted is false AND lastSavedImageUri is null AND !isCapturing)

    Scaffold( // Wrap your screen content with Scaffold
        floatingActionButton = {
            // Show FAB only when camera permission is granted, not capturing, and no image is saved yet
            if (cameraPermissionState.status.isGranted && cameraProvider != null && !isCapturing && lastSavedImageUri == null) {
                FloatingActionButton(
                    onClick = {
                        isCapturing = true
                        captureError = null
                        val photoFile = receiptScanner.createPhotoFile(context)
                        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                        imageCaptureUseCase.takePicture(
                            outputOptions,
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                    isCapturing = false
                                    lastSavedImageUri = output.savedUri
                                    Log.d("ScanScreen", "Image saved: ${output.savedUri}")
                                    // Here you would typically navigate away or update the ViewModel
                                    // to process the image and then potentially clear lastSavedImageUri
                                    // for the next scan.
                                }

                                override fun onError(exc: ImageCaptureException) {
                                    isCapturing = false
                                    captureError = exc.message ?: "Image capture failed"
                                    Log.e("ScanScreen", "Image capture failed", exc)
                                    photoFile.delete()
                                }
                            }
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Filled.CameraAlt, "Scan Receipt")
                }
            }
        }
    ) { innerPadding -> // Content of the Scaffold, respect innerPadding
        Box(modifier = Modifier.padding(innerPadding)) { // Apply innerPadding to your root content
            if (lastSavedImageUri != null && !isCapturing) {
                // --- Condition 1: Full screen list after successful capture ---
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)) {
                    Text(
                        "Receipt Scanned!",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Button(
                        onClick = {
                            lastSavedImageUri = null // Reset to allow re-scan or go back
                            // Potentially also reset other states in ViewModel if needed
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text("Scan Another Receipt")
                    }
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(expenseItemsList) { wallet ->
                            ExpenseListItem(item = wallet)
                        }
                    }
                }
            } else if (isCapturing) {
                // --- Condition 3: UI during capture ---
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                    Text("Saving image...", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 70.dp))
                }
            } else if (!cameraPermissionState.status.isGranted) {
                // --- Condition 2: Permission not granted ---
                Column(modifier = Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .padding(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                                "Camera permission is important for scanning receipts. Please grant the permission."
                            } else {
                                "Camera permission is required to scan receipts."
                            }
                            Text(textToShow, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                                Text("Grant Permission")
                            }
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(expenseItemsList) { wallet ->
                            ExpenseListItem(item = wallet)
                        }
                    }
                }
            } else {
//                TODO: move first camera start to FAB callback
                // --- Default: Camera preview is visible, ready to capture ---
                Box(modifier = Modifier.fillMaxSize()) {
                    val previewView = remember { PreviewView(context) }
                    LaunchedEffect(cameraProvider, lifecycleOwner) {
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

                    // Optional: You can add an overlay or instructions over the camera preview
                    // For example, a semi-transparent box or guidelines.

                    // Display capture error if any (could be a Snackbar or a Text overlay)
                    if (captureError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                                .padding(16.dp)
                                .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f))
                        ) {
                            Text(
                                "Error: $captureError",
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                    // The "Scan Receipt" Button is now a FAB.
                    // The old button location can be removed if the FAB covers its functionality.
                    // If you still want a button at the bottom for some reason, you can keep it,
                    // but usually, a FAB replaces a primary action button like this.
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraProvider?.unbindAll()
        }
    }
}