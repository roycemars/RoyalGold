import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

configurations.all {
    resolutionStrategy {
        force(libs.guava)
    }
}

// Function to safely load properties from local.properties
fun getApiKey(propertyKey: String): String? {
    val properties = Properties() // Use java.util.Properties
    val localPropertiesFile = rootProject.file("local.properties") // Access local.properties at rootProject level
    return if (localPropertiesFile.exists()) {
        FileInputStream(localPropertiesFile).use { fis -> // Use try-with-resources for safely closing the stream
            properties.load(fis)
        }
        properties.getProperty(propertyKey)
    } else {
        // Optionally, try to get from environment variables for CI/CD
        System.getenv(propertyKey)
    }
}

android {
    namespace = "com.roycemars.royalgold"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.roycemars.royalgold"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        defaultConfig {
            // ...
            val geminiApiKey = getApiKey("GEMINI_API_KEY")
            buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")

            val cmcApiKey = getApiKey("CMC_PRO_API_KEY")
            buildConfigField("String", "CMC_PRO_API_KEY", "\"$cmcApiKey\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // For release builds, you might want to ensure the key is present
            // val geminiApiKeyRelease = getApiKey("GEMINI_API_KEY")
            // if (geminiApiKeyRelease == null) {
            //     throw GradleException("GEMINI_API_KEY not found for release build.")
            // }
            // buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKeyRelease\"")
        }
        debug {
            // You can have different logic for debug, e.g., allow a missing key
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.compiler) {
        exclude(group = "com.intellij", module = "annotations")
    }
    ksp(libs.androidx.room.compiler) // Use ksp for the compiler
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.accompanist.permissions)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.guava)

    // Retrofit & Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp) // Retrofit needs OkHttp
    implementation(libs.logging.interceptor) // Optional: For logging network requests

    // Coroutines for asynchronous operations
    implementation(libs.kotlinx.coroutines.core) // Or latest
    implementation(libs.kotlinx.coroutines.android) // Or latest

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // Check your libs.versions.toml
    implementation(libs.androidx.lifecycle.runtime.compose) // For collectAsStateWithLifecycle

    // Coil for Image Loading
    implementation(libs.coil.compose)
}