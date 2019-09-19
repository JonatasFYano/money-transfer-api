FROM java:8-jdk-alpine

WORKDIR /usr/app/money_transfer

COPY money_transfer_api/ /usr/app/money_transfer

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "target/money_transfer_api-1.0-SNAPSHOT-jar-with-dependencies.jar"]
