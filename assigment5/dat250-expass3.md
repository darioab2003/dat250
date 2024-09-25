# DAT250: Software Technology Experiment - Task 5

## MongoDB Setup and Installation

The installation of MongoDB 4.4 Community Edition was completed successfully without any issues. All steps were followed, including validating the installation package with the provided SHA-256 checksum.

### Screenshot 1: Validation of the installation package

![Captura de pantalla 2024-09-24 153102](https://github.com/user-attachments/assets/bf89e2ec-ce45-42ab-8e1a-3296373cf37a)

## Experiment 1: CRUD Operations

In this experiment, I performed the basic CRUD operations (Create, Read, Update, and Delete) using MongoDB. Below are the key operations and their corresponding screenshots.

### 1. Insert Documents
This operation allowed me to insert new documents into a MongoDB collection.
![Experiment1_add_documents](https://github.com/user-attachments/assets/fb8990f9-47d8-4613-95a6-214589589ba7)

Collection.insertMany() can insert multiple documents into a collection:

![Inssert_many](https://github.com/user-attachments/assets/0356d34c-b8f1-424a-9ca5-50d42108158c)

query the collection:

![Consulta_collecion](https://github.com/user-attachments/assets/9c63da17-b097-472a-b72a-f369f859438d)


### 2. Query Documents
I used the `find()` function to query and retrieve all documents from the collection.

Insert:
![Insert_many_2 2](https://github.com/user-attachments/assets/fae7dd73-e641-43ee-b940-8d42348ca85d)

In the following example, the compound query document selects all documents in the collection where the status equals "A" and either qty is less than ($lt) 30 or item starts with the character p:

![status_A_and_qty_less_than_($lt)_30_or_starts_p](https://github.com/user-attachments/assets/9edcce2a-6a40-409a-b416-dbf197638443)


### 3. Update Documents
The `updateOne()` function was used to modify existing documents within the collection.

Insert many and update one:
![Insert_many_and_update1](https://github.com/user-attachments/assets/252b7ddf-572a-49f0-b82c-77b97bd95c0e)

Update many and replace one:
![update_many_replace1](https://github.com/user-attachments/assets/8c905bf5-ddbe-4215-b8ef-bed68798519d)

### 4. Remove Documents
The `deleteOne()` function was used to remove specific documents from the collection.
Insert many and delete many:
![Insert_many_delete_many](https://github.com/user-attachments/assets/4eec20ff-dca8-4c22-a34a-5d8339d37106)

### 5. Bulk Write Operations
The `bulkWrite()` function was used to perform multiple write operations in a single call, including insertions, updates, deletions, and replacements.


## Experiment 2: Aggregation - Map-Reduce

For this experiment, I explored the Map-Reduce aggregation feature in MongoDB. Map-Reduce is useful for processing large data sets and aggregating the results efficiently.

### Screenshot: Map-Reduce Example
*Insert screenshot of the Map-Reduce example here*

### Additional Operation Developed
For the additional operation, I created a Map-Reduce function that counts the occurrences of specific words within a collection of text documents. The results show the frequency of each word.

### Why Map-Reduce is Useful
The Map-Reduce operation is useful because it allows for the efficient processing and aggregation of large data sets. By breaking down tasks into smaller "map" operations and then combining the results through "reduce" operations, MongoDB can handle large-scale data analysis more effectively than performing individual queries.

The resulting collection from my Map-Reduce function provided insights into word frequency, which could be applied in various real-world scenarios, such as analyzing user comments, reviews, or any text-based data.

## Pending Issues

There are no unresolved issues related to this task. All operations were completed successfully without any errors.


