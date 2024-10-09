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


Step 2: Connecting to the Database

After that, I executed the following command to inspect the startup logs of the PostgreSQL process:

```java
sudo docker logs my-postgres
````

The next step was to connect to the PostgreSQL database, as shown in the following screenshot.

![Captura](https://github.com/user-attachments/assets/51b42b07-94be-4c6b-9e10-2fa7133612f6)



After that, I opened my project of the assigment 4 in IntelliJ and connected to the PostgreSQL database and I executed the following SQL command to create a new user:

![Captura de pantalla 2024-10-09 121337](https://github.com/user-attachments/assets/4ee8c763-5881-4f26-b6de-1ca912359263)

Step 3: Modifying persistence.xml

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

Step 4: Testing the Connection
I attempted to run my tests, but they failed. I will add a screenshot of the error message.

![Captura de pantalla 2024-10-09 113946](https://github.com/user-attachments/assets/683e63a6-d386-49e4-9439-4e41485d2cd7)

This configuration generated the files schema.up.sql and schema.down.sql, where the schema.up.sql file contains the necessary CREATE TABLE statements.

![Captura de pantalla 2024-10-07 183616](https://github.com/user-attachments/assets/b6b14dc1-28a6-4505-a158-67dd7eed78ff)


Step 5: Executing the SQL Scripts
Next, I executed the CREATE statements in the database console, as seen in the console output.

![Captura de pantalla 2024-10-09 224019](https://github.com/user-attachments/assets/74bed40b-d448-4200-a2ea-77ab602d2e52)


After running the tests again, everything worked correctly.

![Captura de pantalla 2024-10-09 191840](https://github.com/user-attachments/assets/783674d9-1f5e-4d1d-a1ba-a58284a6e4f9)












