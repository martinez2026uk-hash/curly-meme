#!/bin/sh

# Gradle wrapper script

APP_NAME="Gradle"
GRADLE_VERSION="8.5"
DIST_URL="https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip"

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
MAIN_CLASS="org.gradle.wrapper.GradleWrapperMain"

exec "$JAVA_HOME/bin/java" -classpath "$CLASSPATH" "$MAIN_CLASS" "$@"
