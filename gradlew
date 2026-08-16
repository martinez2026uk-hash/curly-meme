#!/usr/bin/env sh

APP_NAME="Gradle"
GRADLE_VERSION="8.5"

PRG="$0"
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done
SAVED="`pwd`"
cd "`dirname "$PRG"`" > /dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" > /dev/null

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
MAIN_CLASS="org.gradle.wrapper.GradleWrapperMain"

if [ -z "$JAVA_HOME" ] ; then
    if [ -x "/usr/bin/java" ] ; then
        JAVA_HOME=""
    fi
fi

if [ -z "$JAVA_HOME" ] ; then
    echo "WARNING: JAVA_HOME is not set."
fi

exec "$JAVA_HOME/bin/java" -classpath "$CLASSPATH" "$MAIN_CLASS" "$@"
