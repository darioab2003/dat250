# Assignment 7

## Introduction
In this assignment, I focused on containerizing a Spring Boot application with PostgreSQL using Docker. I installed the PostgreSQL Docker image, configured the application to connect to the database, and documented the challenges and solutions encountered throughout the process. This report outlines the steps taken during the assignment, emphasizing key configurations and issues resolved.

## Step 1: Installing PostgreSQL Docker Image
The first thing I did was install the PostgreSQL Docker image using the following command:

```java
docker pull postgres
````

However, I encountered an issue because I needed to add sudo to run the command successfully:

```java
sudo docker pull postgres
````

After that, I ran the following command with sudo to start the PostgreSQL container:

```java
docker run -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin_password -e POSTGRES_DB=mydb -d --name my-postgres --rm postgres
````

To verify that the container was running, I used the command:

```java
sudo docker ps
````
and the result was:

![Captura de pantalla 2024-10-07 171652](https://github.com/user-attachments/assets/902d6a04-9a6c-4c43-9388-9c0a8cee02a9)


## Step 2: Connecting to the Database

After that, I executed the following command to inspect the startup logs of the PostgreSQL process:

```java
sudo docker logs my-postgres
````

The next step was to connect to the PostgreSQL database, as shown in the following screenshot.

![Captura](https://github.com/user-attachments/assets/51b42b07-94be-4c6b-9e10-2fa7133612f6)



After that, I opened my project of the assigment 4 in IntelliJ and connected to the PostgreSQL database and I executed the following SQL command to create a new user:

![Captura de pantalla 2024-10-09 121337](https://github.com/user-attachments/assets/4ee8c763-5881-4f26-b6de-1ca912359263)

## Step 3: Modifying persistence.xml

Next, I modified the persistence.xml file by replacing the old connection parameters that were used for H2 with the PostgreSQL connection. The previous configuration looked like this:

```java
<property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
<property name="hibernate.connection.driver_class" value="org.h2.Driver"/>
<property name="hibernate.connection.url" value="jdbc:h2:file:./DB;DB_CLOSE_DELAY=-1"/>
````

I updated it to the following PostgreSQL configuration:

```java
<property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
<property name="hibernate.connection.driver_class" value="org.postgresql.Driver"/>
<property name="hibernate.connection.url" value="jdbc:postgresql://127.0.0.1:5432/postgres"/>
<property name="hibernate.connection.username" value="jpa_client"/>
<property name="hibernate.connection.password" value="secret"/>
````

## Step 4: Testing
I attempted to run my tests, but they failed. I will add a screenshot of the error message.

![Captura de pantalla 2024-10-09 113946](https://github.com/user-attachments/assets/683e63a6-d386-49e4-9439-4e41485d2cd7)

This configuration generated the files schema.up.sql and schema.down.sql, where the schema.up.sql file contains the necessary CREATE TABLE statements.

![Captura de pantalla 2024-10-07 183616](https://github.com/user-attachments/assets/b6b14dc1-28a6-4505-a158-67dd7eed78ff)


## Step 5: Executing the SQL Scripts
Next, I executed the CREATE statements in the database console, as seen in the console output.

![Captura de pantalla 2024-10-09 224019](https://github.com/user-attachments/assets/74bed40b-d448-4200-a2ea-77ab602d2e52)

## Step 6: Testing
After running the tests again, everything worked correctly.

![Captura de pantalla 2024-10-09 191840](https://github.com/user-attachments/assets/783674d9-1f5e-4d1d-a1ba-a58284a6e4f9)



# Building your own dockerized application

In this task, we aimed to containerize our Spring Boot application from Assignment 3. The goal was to package the application into a Docker image, making it easier to distribute and run.

## Step 1: Setting Up the Dockerfile
We started by opening the project from Assignment 3 and creating a new Dockerfile. Below is the content of the Dockerfile that we included:

````java
FROM eclipse-temurin:21-jdk AS build 

WORKDIR /app

COPY . .

RUN ./gradlew bootJar

FROM eclipse-temurin:21-jre-alpine 

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

````

![hoba](https://github.com/user-attachments/assets/e0e79fd0-8403-4898-bcb3-1f6aa7c66c70)


## Step 2: Building the Docker Image
Once the Dockerfile was set up, the next step was to build the Docker image. We ran the following command to do this:

````java
docker build -t my-spring-boot-app .
````

## Step 3: Running the Docker Container
After successfully building the image, we ran the application using the following command:

````java
docker run -p 8080:8080 my-spring-boot-app
````

This allowed the Spring Boot application to run in a Docker container, making it accessible on port 8080.

## Step 4: Testing the Application

To verify the application is working correctly, we conducted a test using a web browser (Bruno in this case) by accessing the application at http://localhost:8080. The test confirmed that the Spring Boot application was running as expected, as shown in the image below:


![3](https://github.com/user-attachments/assets/3d21ce34-8a6d-4756-9d22-bbd11ff4c75a)


![Captura de pantalla 2024-10-10 122806](https://github.com/user-attachments/assets/c6f2732f-bc16-417e-866b-0910148e7082)




