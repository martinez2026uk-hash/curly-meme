# Curly Meme

Android application built with Kotlin and the Android Gradle Plugin.

## Prerequisites

- Android SDK Build-Tools 34
- Android SDK Platform 34
- Java 17 (JDK)
- Android SDK command-line tools (`sdkmanager`)

## Setup

1. Set the `ANDROID_HOME` environment variable to your Android SDK location:
   ```bash
   export ANDROID_HOME=$HOME/Android/Sdk
   export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools
   ```

2. Install required SDK components:
   ```bash
   sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
   ```

## Build

Run the following command to build the debug APK:

```bash
./gradlew assembleDebug
```

The output APK will be located at `app/build/outputs/apk/debug/app-debug.apk`.

## Clean

```bash
./gradlew clean
```

## Notes

- Building requires the Android SDK to be installed and `ANDROID_HOME` set.
- The Gradle wrapper (`gradlew`) will download Gradle 8.4 automatically.
