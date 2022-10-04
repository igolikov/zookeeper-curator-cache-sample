ARG IMAGE=openjdk:17-slim
FROM $IMAGE

ARG APP_NAME
ARG MAIN_CLASS

ENV COMMON_CONFIG_DIR="/$APP_NAME/config"
ENV JAVA_OPTS="$JAVA_OPTS --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED"
ENV JAVA_OPTS="$JAVA_OPTS -XshowSettings:vm"
ENV JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,address=*:8000,suspend=n"
# Class path to jar file, related libraries, configuration directories that need to be used
# to start application
ENV CLASSPATH="/$APP_NAME/$APP_NAME.jar:/$APP_NAME/lib/*:$COMMON_CONFIG_DIR"
ENV MAIN_CLASS_ENV="$MAIN_CLASS"

# Create directory to hold the application and all its contents in the Docker image.
# Copy all the static contents to be included in the Docker image.
COPY lib /$APP_NAME/lib
COPY $APP_NAME.jar /$APP_NAME/

# Args unavailable at the moment when container is startingq
CMD java $JAVA_OPTS -classpath $CLASSPATH "$MAIN_CLASS_ENV"