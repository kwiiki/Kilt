plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("com.google.dagger.hilt.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
//    kotlin("plugin.serialization") version "2.0.20"
}

android {
    namespace = "com.example.kilt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kilt"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.lifecycle.runtime.compose)

    implementation (libs.androidx.navigation.compose)

    implementation (libs.coil.compose)

    implementation (libs.androidx.preference.ktx)

    implementation (libs.material3)

    implementation (libs.androidx.constraintlayout.compose)


    implementation(libs.com.squareup.retrofit2)
    implementation(libs.json.converter)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation (libs.converter.moshi)


    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")


    implementation ("androidx.paging:paging-runtime:3.3.2")
    implementation ("androidx.paging:paging-compose:3.3.2")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.22")

//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")



}