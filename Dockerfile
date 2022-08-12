FROM openjdk:17.0.1
ADD /target/AuthorizationJWT-0.0.1-SNAPSHOT.jar AuthorizationJWT.jar
ENTRYPOINT ["java", "-jar", "AuthorizationJWT.jar"]