@rem Gradle wrapper script

setlocal

set APP_NAME=Gradle
set GRADLE_VERSION=8.5

set CLASSPATH=%~dp0..\gradle\wrapper\gradle-wrapper.jar
set MAIN_CLASS=org.gradle.wrapper.GradleWrapperMain

"%JAVA_HOME%\bin\java" -classpath "%CLASSPATH%" "%MAIN_CLASS%" %*
