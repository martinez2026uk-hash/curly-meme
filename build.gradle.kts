plugins {
    id("com.android.application") version "8.4.0"
    kotlin("android")
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }
}

repositories {
    google()
    mavenCentral()
}
