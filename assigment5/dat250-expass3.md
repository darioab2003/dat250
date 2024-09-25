# DAT250: Software Technology Experiment - Task 5

## MongoDB Setup and Installation

The installation of MongoDB 4.4 Community Edition was completed successfully without any issues. All steps were followed, including validating the installation package with the provided SHA-256 checksum.

### Screenshot 1: Validation of the installation package

![Captura de pantalla 2024-09-24 153102](https://github.com/user-attachments/assets/bf89e2ec-ce45-42ab-8e1a-3296373cf37a)

## Experiment 1: CRUD Operations

In this experiment, I performed the basic CRUD operations (Create, Read, Update, and Delete) using MongoDB. Below are the key operations and their corresponding screenshots.

### 1. Insert Documents
This operation allowed me to insert new documents into a MongoDB collection.
*Insert screenshot of the insert operation here*

### 2. Query Documents
I used the `find()` function to query and retrieve all documents from the collection.
*Insert screenshot of the query operation here*

### 3. Update Documents
The `updateOne()` function was used to modify existing documents within the collection.
*Insert screenshot of the update operation here*

### 4. Remove Documents
The `deleteOne()` function was used to remove specific documents from the collection.
*Insert screenshot of the delete operation here*

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


