FROM maven:3.9.8-eclipse-temurin-21 AS maven-builder
WORKDIR /app

COPY pom.xml /app
COPY src /app/src

RUN mvn clean install -U -DskipTests

# Шаг 2: Сборка и запуск Backend (Java)
FROM openjdk:21
WORKDIR /app

COPY --from=maven-builder /app/target/taxi-quality-assessment-service-*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

# Шаг 3: Сборка Backend (дополнительный сервис, если требуется)
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Шаг 4: Запуск второго Backend-сервиса (если необходимо)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/taxi-aggregator-service-*.jar aggregator-service.jar

ENV JAVA_OPTS="-Xms128m -Xmx256m"
EXPOSE 8081

CMD ["sh", "-c", "java $JAVA_OPTS -jar aggregator-service.jar"]

# Шаг 5: Сборка Frontend (Node.js)
FROM node:18-alpine as build

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . ./

RUN npm run build

# Шаг 6: Сервинг фронтенда через Nginx
FROM nginx:alpine

COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]