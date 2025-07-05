# --------------------------
# ✅ Kotlin / Coroutines
# --------------------------
-keepclassmembers class kotlinx.coroutines.** {
    *;
}
-dontwarn kotlinx.coroutines.**

# --------------------------
# ✅ Hilt / Dagger
# --------------------------
-keep class dagger.** { *; }
-keep interface dagger.** { *; }
-keep class javax.inject.** { *; }
-dontwarn dagger.**
-dontwarn javax.inject.**

# Hilt-generated code
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-keep class * extends dagger.hilt.android.HiltAndroidApp { *; }

# --------------------------
# ✅ Room
# --------------------------
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# For generated Room code
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# TypeConverter 객체 자체 보존
-keep class com.aube.mysize.data.database.TypeConverter { *; }

# TypeConverter 내부의 Room 어노테이션 메서드 보존
-keepclassmembers class com.aube.mysize.data.database.TypeConverter {
    @androidx.room.TypeConverter <methods>;
}

# --------------------------
# ✅ Retrofit & Gson
# --------------------------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Gson serialization
-keep class com.google.gson.** { *; }
-keep class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# --------------------------
# ✅ Firebase
# --------------------------
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

-keep class com.google.firebase.storage.** { *; }
-dontwarn com.google.firebase.storage.**

# ML Kit (Text Recognition)
-keep class com.google.mlkit.** { *; }
-dontwarn com.google.mlkit.**

# --------------------------
# ✅ DTO
# --------------------------
-keep class com.aube.mysize.data.model.dto.** { *; }
-keepclassmembers class com.aube.mysize.data.model.dto.** {
    public <init>(...);
}
-keep class com.aube.mysize.domain.model.clothes.LinkedSizeGroup { *; }
-keepclassmembers class com.aube.mysize.domain.model.clothes.LinkedSizeGroup {
    public <init>(...);
}

# --------------------------
# ✅ Coil
# --------------------------
-keep class coil.** { *; }
-dontwarn coil.**

# --------------------------
# ✅ uCrop & canhub Android Image Cropper
# --------------------------
-keep class com.yalantis.ucrop.** { *; }
-keep class com.canhub.cropper.** { *; }
-dontwarn com.yalantis.ucrop.**
-dontwarn com.canhub.cropper.**

# --------------------------
# ✅ Accompanist / Lottie / Compose
# --------------------------
-dontwarn com.google.accompanist.**
-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**

# --------------------------
# ✅ Timber
# --------------------------
-dontwarn timber.log.**

# --------------------------
# ✅ AndroidX / Compose
# --------------------------
# Prevent removal of @Composable functions
-keep class androidx.compose.runtime.** { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}
-dontwarn androidx.compose.**

# Prevent removal of preview annotations
-keep class androidx.compose.ui.tooling.preview.** { *; }
-keep class androidx.compose.ui.tooling.** { *; }

# --------------------------
# ✅ Google Play Ads
# --------------------------
-keep class com.google.android.gms.ads.** { *; }
-dontwarn com.google.android.gms.ads.**

# --------------------------
# ✅ DataStore
# --------------------------
-dontwarn androidx.datastore.**
-keep class androidx.datastore.** { *; }

# --------------------------
# ✅ SplashScreen API
# --------------------------
-keep class androidx.core.splashscreen.** { *; }
-dontwarn androidx.core.splashscreen.**

# --------------------------
# ✅ AppCompat, Lifecycle
# --------------------------
-dontwarn androidx.appcompat.**
-dontwarn androidx.lifecycle.**

