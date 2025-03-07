plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "android.saswat.winmyargument"
    compileSdk = 35

    defaultConfig {
        applicationId = "android.saswat.winmyargument"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    }
}

dependencies {

   implementation(libs.androidx.navigation.compose)
   implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
   implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
   implementation("com.google.firebase:firebase-analytics")
   implementation("com.google.firebase:firebase-auth")
   implementation("com.google.android.gms:play-services-auth:21.2.0")
   implementation("com.google.firebase:firebase-firestore")
   implementation(libs.androidx.core.ktx)
   implementation("androidx.appcompat:appcompat:1.6.1")
   implementation("androidx.core:core:1.12.0")
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
   implementation("androidx.core:core-splashscreen:1.0.1") {
     // Force consistent dependency resolution
   }
}