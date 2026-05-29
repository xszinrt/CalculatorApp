#!/bin/sh

# Gradle wrapper script

# Detect OS
case "$(uname)" in
  Darwin*)
    GRADLE_OPTS="$GRADLE_OPTS -Xdock:name=Gradle"
    ;;
esac

# Find Gradle
if [ -f gradlew.bat ]; then
    GRADLE_CMD="./gradlew.bat"
else
    GRADLE_CMD="gradle"
fi

# Run Gradle
exec $GRADLE_CMD "$@"
