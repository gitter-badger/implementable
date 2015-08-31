FROM java:8u66-jdk

# Copy source code
RUN mkdir -p /src/src && mkdir -p /src/gradle
COPY gradle /src/gradle
COPY src /src/src
COPY build.gradle gradlew settings.gradle /src/
WORKDIR /src

# Build & remove the source code
RUN ./gradlew installDist && \
    mv ./build/install/implementable-idl / && \
    rm -rf /src

VOLUME /workdir
WORKDIR /workdir
ENTRYPOINT ["/implementable-idl/bin/implementable-idl"]

