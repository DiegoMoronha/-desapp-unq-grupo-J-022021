FROM openjdk:8 

COPY target/backend-desapp-api.jar backend-desapp-api.jar
EXPOSE 3001 
ENTRYPOINT [ "java","-jar","/backend-desapp-api.jar" ]