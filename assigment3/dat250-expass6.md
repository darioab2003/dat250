# DAT250: Software Technology Experiment Assignment 3

## Introduction

The goal of this assignment was to develop a web-based graphical user interface for the REST API created in the previous assignment, which allows users to create a user, create a poll, and vote on a poll. The GUI was implemented as a Single Page Application (SPA) using [chosen framework/library].

## Implementation

For this assignment, the following tasks were performed:

1. **Setup and Framework**: Initialized a new Node.js project using Vite and set up the chosen SPA framework/library. Removed pre-generated starter code to start from a clean slate.
2. **Components Developed**:
   - `CreateUserComponent`: Allows the creation of new users.
   - `CreatePollComponent`: Enables the creation of new polls along with options.
   - `VoteComponent`: Allows users to vote on existing polls.
3. **Integration**: Utilized the `fetch()` API to integrate the frontend with the backend REST API. Implemented POST and PUT requests for creating polls and voting, respectively.
4. **CORS Configuration**: Added `@CrossOrigin` annotations to backend controllers to enable cross-origin requests during development.

## Technical Issues and Resolutions

- **Selecting Poll Options**: Encountered issues with selecting and adding vote options to a poll. This was resolved by adjusting the component logic to properly handle the selection and addition of options.
- **Voting Data Storage**: Faced problems with storing vote information, where votes were not being saved correctly. The issue was resolved by ensuring that the correct option ID was sent with the vote request and properly handled on the backend.

## Pending Issues

- Currently, there are no unresolved issues. The frontend and backend integration has been completed successfully, and all functionalities have been tested.
- Front-end Code Submission: Due to the size limitations, I was unable to include the entire front-end codebase. The following directories were not added: `.idea`, `.vscode`, and `node_modules`.

## Code Links

