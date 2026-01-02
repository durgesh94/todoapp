
# ---- Build stage: compile the Spring Boot app ----
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Skip tests if build times out; remove -DskipTests for production
RUN mvn -q -DskipTests clean package

# ---- Run stage: lightweight JRE image ----
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the fat jar from the build stage. Adjust the jar name if different.
COPY --from=build /app/target/todoapp-0.0.1-SNAPSHOT.jar app.jar

# Render injects PORT; expose for clarity (Docker uses EXPOSE only as metadata)
ENV PORT=8080
EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["java","-jar","/app/app.jar"]
