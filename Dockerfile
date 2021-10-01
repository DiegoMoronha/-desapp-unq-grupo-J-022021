FROM openjdk:8 

ADD target/backend-desapp-api.jar backend-desapp-api.jar
EXPOSE 3001 
ENTRYPOINT [ "java","-jar","backend-desapp-api.jar" ]