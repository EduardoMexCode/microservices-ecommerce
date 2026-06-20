# --- ETAPA 1: BUILD (La Construcción) --
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
LABEL authors="EduMex"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTest

# --- ETAPA 2: RUNTIME (Producto final) --
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app
COPY --from=builder /app/target/*.jar product.jar
ENTRYPOINT ["java", "-jar", "product.jar"]