plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {

    val compileSDK: Int by rootProject.extra
    setCompileSdkVersion(compileSDK)

    val minSDK: Int by rootProject.extra
    val targetSDK: Int by rootProject.extra
    defaultConfig {
        applicationId = "com.john6.example.scenenavigation"
        minSdk = minSDK
        targetSdk = targetSDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    setDynamicFeatures(mutableSetOf(":feature_ngapi", ":feature_ktv"))

    flavorDimensions += listOf("ktv")

    productFlavors {
        register("ktv") {
            dimension = "ktv"
            applicationIdSuffix = ".ktv"
            versionNameSuffix = "-ktv"
            val ktvVersionCode: Int by rootProject.extra
            val ktvVersionName: String by rootProject.extra
            versionCode = ktvVersionCode
            versionName = ktvVersionName
        }
        register("ngapi") {
            dimension = "ktv"
            applicationIdSuffix = ".ngapi"
            versionNameSuffix = "-ngapi"

            val ngApiVersionCode: Int by rootProject.extra
            val ngApiVersionName: String by rootProject.extra
            versionCode = ngApiVersionCode
            versionName = ngApiVersionName
        }
        register("full") {
            dimension = "ktv"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

dependencies {
    api(project(":base"))
}