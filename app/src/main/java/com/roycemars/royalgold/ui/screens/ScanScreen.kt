package com.roycemars.royalgold.ui.screens // Adjust package name

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
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import androidx.core.content.ContextCompat
import java.io.File
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.semantics.error
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.intl.Locale
import com.google.accompanist.permissions.*
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.text.format

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    var isCapturing by remember { mutableStateOf(false) }
    var captureError by remember { mutableStateOf<String?>(null) }
    var lastSavedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imageCaptureUseCase = remember { ImageCapture.Builder().build() }
    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) } // Hold the provider

    // Request permission and setup camera provider
    LaunchedEffect(key1 = cameraPermissionState.status) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        } else {
            val providerFuture = ProcessCameraProvider.getInstance(context)
            providerFuture.addListener({
                cameraProvider = providerFuture.get()
            }, ContextCompat.getMainExecutor(context))
        }
    }

    if (cameraPermissionState.status.isGranted) {
        Box(modifier = Modifier.fillMaxSize()) {
            val previewView = remember { PreviewView(context) }

            // Camera binding effect - re-bind if provider or lifecycleOwner changes
            LaunchedEffect(cameraProvider, lifecycleOwner) {
                cameraProvider?.let { provider ->
                    try {
                        provider.unbindAll() // Unbind previous use cases
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
                verticalArrangement = Arrangement.Bottom // Place controls at the bottom
            ) {
                if (captureError != null) {
                    Text("Error: $captureError", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (lastSavedImageUri != null) {
                    Text("Last image: $lastSavedImageUri") // Show confirmation
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        isCapturing = true
                        captureError = null
                        lastSavedImageUri = null

                        val photoFile = createPhotoFile(context)
                        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
                        val mainExecutor = ContextCompat.getMainExecutor(context)

                        imageCaptureUseCase.takePicture(
                            outputOptions,
                            mainExecutor,
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                    isCapturing = false
                                    lastSavedImageUri = output.savedUri
                                    Log.d("ScanScreen", "Image saved: ${output.savedUri}")
                                    // TODO: Send output.savedUri to Gemini API
                                }

                                override fun onError(exc: ImageCaptureException) {
                                    isCapturing = false
                                    captureError = exc.message ?: "Image capture failed"
                                    Log.e("ScanScreen", "Image capture failed", exc)
                                }
                            }
                        )
                    },
                    enabled = !isCapturing && cameraProvider != null, // Enable only if camera is ready
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isCapturing) "Capturing..." else "Scan Receipt")
                }
            }
        }
    } else {
        // Permission not granted UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Camera permission is required to scan receipts.")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Grant Permission")
            }
            if (cameraPermissionState.status.shouldShowRationale) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Please grant camera permission to use the scanner.")
            }
        }
    }

    // Clean up camera resources
    DisposableEffect(Unit) {
        onDispose {
            cameraProvider?.unbindAll()
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun createPhotoFile(context: android.content.Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir = context.externalCacheDir ?: context.cacheDir // Prefer external cache
    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}