FROM maven:3.9-eclipse-temurin-21-alpine
WORKDIR /app
COPY . .
RUN mvn clean install
CMD ["mvn" , "spring-boot:run"]
EXPOSE 8080