plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.aube.mysize"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aube.mysize"
        minSdk = 28
        targetSdk = 35
        versionCode = 6
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk {
                debugSymbolLevel = "FULL"
            }
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
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(libs.hilt.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.play.services.mlkit.text.recognition.korean)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.storage)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.text.recognition)
    implementation(libs.ucrop)
    implementation(libs.canhub.android.image.cropper)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.lottie.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.timber)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.google.firebase.storage.ktx)
    implementation(libs.play.services.auth)

    implementation(libs.play.services.ads)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}