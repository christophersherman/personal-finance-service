 FROM openjdk:17-jdk-alpine   
 VOLUME /tmp 
 ADD target/pfinance-application-service.jar / 
 ENTRYPOINT ["java","-jar","/pfinance-application-service.jar"]  
