# DAT250 Experiment 7 Report

## Technical Issues Encountered

During the completion of the RabbitMQ "Hello World" tutorial, I encountered several technical issues:

1. **Docker Setup**: Initially, I faced a permission error when trying to run the RabbitMQ container. This was caused by insufficient permissions to access Docker's socket. After some research, I resolved the issue by adding my user to the `docker` group and restarting the session.

2. **Gradle Configuration**: While setting up the project to run the RabbitMQ tutorial in Java, I encountered an issue where the `run` task was missing. I fixed this by configuring the `build.gradle` file to include the correct main class for execution.

3. **Connection to RabbitMQ**: After setting up RabbitMQ on Docker, I was stuck at the server startup log, which led me to investigate if RabbitMQ was properly listening on port 5672. The issue was resolved after confirming that the container was running correctly and the ports were correctly mapped.

## Code Links

The source code for all experiments, including the "Hello World" RabbitMQ tutorial, can be found at the following link:

experiment1:


## Unresolved Issues

At this time, there are no unresolved issues. The RabbitMQ "Hello World" tutorial is functioning as expected, and both the producer and consumer programs are working correctly.