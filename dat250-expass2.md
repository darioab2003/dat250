## DAT250: Software Technology Experiment Assignment 2

### Introduction

The goal of this assignment is to implement a simple REST API for a Poll app using Spring Boot.

### Implementation

For this assignment, the following tasks were performed:

1. **Domain Model**: Defined a domain model consisting of `User`, `Poll`, `VoteOption`, and `Vote`.
2. **CRUD Operations**: Implemented CRUD operations for each entity using Spring Boot's `@RestController` and respective `@GetMapping`, `@PostMapping`, `@PutMapping`, and `@DeleteMapping` annotations.
3. **Serialization**: Ensured proper JSON serialization and deserialization to handle association cycles using annotations like `@JsonIgnore`, `@JsonIdentityInfo`, `@JsonIdentityReference`, `@JsonManagedReference`, and `@JsonBackReference`.
4. **Exception Handling**: Added global exception handling directly in the controller to provide user-friendly error messages.
5. **Automated Testing**: Created automated tests for the implemented REST API endpoints using Spring's `RestClient`.

The code is structured in separate controller classes for each entity, ensuring modularity and clarity.

### Technical Issues and Resolutions

During the installation and setup, we encountered an issue with the Integrated Development Environment (IDE). The computer was unable to open the program, which blocked further progress. We resolved this by downloading and installing another version of the IDE that was also acceptable for the course requirements. This allowed us to proceed with the project without further issues.

Testing Base URL: Initially, we were unable to execute tests in Bruno. We discovered that the issue was due to an incorrect base URL. By setting the base URL to `localhost:8080`, we were able to run the tests successfully.


### Pending Issues

Currently, there are no unresolved issues with this assignment. All required functionalities were implemented and tested successfully.
