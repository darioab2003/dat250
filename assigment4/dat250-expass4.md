
# Experiment Report - Exercise 4

## 1. Database Used

In this experiment, we used an H2 database, which is an in-memory relational database. 
The database is initialized and executed when the application starts, thanks to the configuration in the `persistence.xml` file. 
The persistence unit `jpa-tutorial` is defined, along with the necessary Hibernate properties for handling the database interactions. 
Specifically, the `hibernate.hbm2ddl.auto` property is set to `update`, allowing the schema to be automatically generated or updated as needed.

## 2. SQL to Create the `Customer` Table

Here is the SQL generated by Hibernate to create the `Customer` table:

```sql
CREATE TABLE Customer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE customer_address (
    customer_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL
);

CREATE TABLE customer_creditcard (
    customer_id BIGINT NOT NULL,
    creditcard_id BIGINT NOT NULL
);
```

This structure includes the customer table and two join tables for the relationships with addresses and credit cards.

## 3. Inspecting the Database

To inspect the tables created in the database, I used Hibernate's logging feature by setting the following property in the `persistence.xml` file:

```xml
<property name="hibernate.show_sql" value="true"/>
```

This allowed me to see the SQL statements generated by Hibernate, which helped to verify that the following tables were created as expected:

https://github.com/darioab2003/dat250/blob/main/assigment4/SQl


The tables created correspond closely to the initial expectations, following the structure and relationships defined in the entity classes.

## 4. Technical Issues Encountered

There were no issues with the installation or configuration of Java Persistence Architecture (JPA) and Hibernate. However, I encountered some challenges during development, particularly in managing the comparison of objects. Initially, I was not using the appropriate structures, such as using `Set` instead of `Collection` in some relationships, which caused issues with comparisons. After switching to the correct structures (e.g., using `Set` for addresses and `List` for credit cards), the problem was resolved.

## 5. Code from Experiment 2

https://github.com/darioab2003/dat250/tree/main/assigment4/dat250-jpa-tutorial-master

## 6. Outstanding Issues

As of the completion of this report, no outstanding issues remain in this task. All problems encountered have been resolved, and the database and its relationships have been correctly implemented.
