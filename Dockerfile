FROM java:openjdk-8
MAINTAINER nedyalkop@gmail.com
ENV DB_HOST localhost
WORKDIR /
COPY target/demo-0.0.1-SNAPSHOT.jar /home/application/demo-0.0.1-SNAPSHOT.jar
EXPOSE 8082
CMD ["java","-jar","/home/application/demo-0.0.1-SNAPSHOT.jar"]