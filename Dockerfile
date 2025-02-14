# Etapa de construcción
FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar el archivo pom.xml y las fuentes para evitar reconstrucciones innecesarias
COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline
# Construir el proyecto (sin ejecutar los tests para acelerar el proceso de construcción)
RUN mvn clean package -DskipTests


# Etapa de ejecución
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto donde se ejecutará la aplicación
EXPOSE 3000

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]