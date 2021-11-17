plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
}
android {
    val compileSDK: Int by rootProject.extra
    setCompileSdkVersion(compileSDK)

    val minSDK: Int by rootProject.extra
    defaultConfig {
        minSdk = minSDK
    }
    buildTypes {
        release {
            // You must use the following property to specify additional ProGuard
            // rules for feature modules.
            proguardFiles("proguard-rules-dynamic-features.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    flavorDimensions.add("ktv")
    productFlavors {
        create("full")
        create("ktv")
        create("ngapi")
    }
}

dependencies {
    implementation(project(":app"))
}