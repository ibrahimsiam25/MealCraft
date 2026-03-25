plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.siam.mealcraft"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.siam.mealcraft"
        minSdk = 24
        targetSdk = 36
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
    implementation(libs.constraintlayout)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.common.jvm)
    implementation(libs.room.rxjava3)
    annotationProcessor(libs.room.compiler)

    // RxJava
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.rxjava3.retrofit.adapter)

    // UI
    implementation(libs.recyclerview)
    implementation(libs.glide)
    implementation(libs.lottie)
    implementation(libs.core)
    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)


    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}