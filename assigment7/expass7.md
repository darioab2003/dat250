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
After that, I opened my project in IntelliJ and connected to the PostgreSQL database. I executed the following SQL command to create a new user:




















