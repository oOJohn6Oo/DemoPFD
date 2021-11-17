plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    val compileSDK :Int by rootProject.extra
    setCompileSdkVersion(compileSDK)

    val minSDK :Int by rootProject.extra
    val targetSDK :Int by rootProject.extra
    defaultConfig {
        minSdk = minSDK
        targetSdk = targetSDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
    }
}

dependencies {

    api ("androidx.core:core-ktx:1.7.0")
    api ("com.google.android.material:material:1.4.0")

    val navVersion:String by rootProject.extra
    api("androidx.navigation:navigation-fragment-ktx:$navVersion")
//    api("androidx.navigation:navigation-ui-ktx:$nav_version")
    // Feature module Support
    api("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")


    testApi("junit:junit:4.13.2")
    androidTestApi ("androidx.test.ext:junit:1.1.3")
    androidTestApi ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestApi ("androidx.annotation:annotation:1.3.0")
}