FROM maven:3.8.1-openjdk-8-slim

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:resolve

COPY . .

RUN mvn clean compile

CMD ["mvn", "test"]
