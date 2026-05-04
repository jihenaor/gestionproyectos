# ── Etapa prebuilt: copia JAR externo (construido previamente con Gradle del sistema) ──
FROM eclipse-temurin:21-jre-alpine AS prebuilt

WORKDIR /app

COPY build/libs/*.jar app.jar

RUN addgroup -S appgrp && adduser -S appusr -G appgrp \
    && chown appusr:appgrp app.jar \
    && chmod 444 app.jar

USER appusr

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]

# ── Etapa prebuilt: copia JAR externo (DOCKER_TARGET=prebuilt en CI) ──────────
FROM eclipse-temurin:21-jre-alpine AS prebuilt-ci

WORKDIR /app

COPY prebuilt-jars/*.jar app.jar

RUN addgroup -S appgrp && adduser -S appusr -G appgrp \
    && chown appusr:appgrp app.jar \
    && chmod 444 app.jar

USER appusr

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]

# ── Etapa final: compila y empaqueta (DOCKER_TARGET=final) ───────────────────
FROM eclipse-temurin:21-jre-alpine AS final

RUN addgroup -S appgrp && adduser -S appusr -G appgrp

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

RUN chown appusr:appgrp app.jar \
    && chmod 444 app.jar

USER appusr

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]