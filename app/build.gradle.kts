plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "sandeep.task.contentanalyzer"
    compileSdk = 34

    defaultConfig {
        applicationId = "sandeep.task.contentanalyzer"
        minSdk = 25
        targetSdk = 34
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    // File chooser compatibility
    implementation("androidx.fragment:fragment:1.5.5")

    // ML Kit Text Recognition
    implementation("com.google.mlkit:text-recognition:16.0.0")

    // iText library for PDF text extraction
    implementation("com.itextpdf:itext7-core:7.1.15")
    // Volley for API requests
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.airbnb.android:lottie:6.6.2")
    implementation("io.noties.markwon:core:4.6.2")
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}