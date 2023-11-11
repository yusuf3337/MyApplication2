plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") version "4.3.15"
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 33
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    namespace = "http://schemas.android.com/apk/res-auto"
}

    dependencies {
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.10.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
        implementation("androidx.viewpager2:viewpager2:1.0.0")
        implementation("com.google.android.gms:play-services-maps:18.2.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

        implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
        implementation("com.google.firebase:firebase-analytics-ktx")
        implementation("com.google.firebase:firebase-auth-ktx")
        implementation("com.google.firebase:firebase-firestore-ktx")
        implementation("com.google.firebase:firebase-messaging-ktx")
        implementation("com.google.firebase:firebase-storage-ktx")
        implementation("com.squareup.picasso:picasso:2.71828")
        implementation("com.google.firebase:firebase-database-ktx")
        implementation("com.onesignal:OneSignal:5.0.4")
        implementation("id.zelory:compressor:3.0.0")
    }

