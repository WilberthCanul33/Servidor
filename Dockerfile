FROM openjdk:17

WORKDIR /app

COPY . .

RUN javac server/*.java

EXPOSE 12345

CMD ["java", "ServerMain"]

