# CurlyMeme

A simple Android application built with Kotlin and Gradle.

## Prerequisites

- Android SDK with API level 34 (Android 14) installed
- Android SDK Build-Tools 34 installed
- Java 17 (or newer) installed
- `JAVA_HOME` set to your JDK installation directory

## Setting Up the Environment

1. Install the Android SDK command-line tools from [developer.android.com](https://developer.android.com/studio#command-tools).

2. Set the `ANDROID_HOME` environment variable to your Android SDK installation path:
   ```bash
   export ANDROID_HOME=$HOME/Android/Sdk
   export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools
   ```

3. Install the required SDK platform and build tools:
   ```bash
   sdkmanager "platforms;android-34" "build-tools;34.0.0"
   ```

## Building the APK

Run the debug build:
```bash
./gradlew assembleDebug
```

The debug APK will be generated at:
```
app/build/outputs/apk/debug/app-debug.apk
```

## Installing the APK

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Project Structure

```
.
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── app/
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/curlymeme/MainActivity.kt
│           └── res/
│               ├── layout/activity_main.xml
│               ├── mipmap-anydpi-v26/
│               ├── drawable/
│               └── values/
│                   ├── colors.xml
│                   ├── strings.xml
│                   ├── styles.xml
│                   └── themes.xml
```
