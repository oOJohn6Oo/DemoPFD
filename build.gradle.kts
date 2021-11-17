buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}

tasks{
    val clean by registering(Delete::class){
        delete(rootProject.buildDir)
    }
}

extra["minSDK"] = 21
extra["targetSDK"] = 31
extra["compileSDK"] = 31

extra["navVersion"] = "2.3.5"


extra["ngApiVersionCode"] = 1
extra["ngApiVersionName"] = "1.0"

extra["ktvVersionCode"] = 1
extra["ktvVersionName"] = "1.0"