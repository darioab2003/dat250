## Experiment 1: RabbitMQ Setup

### Technical Issues Encountered

During the setup and execution of RabbitMQ for the "Hello World" tutorial, I encountered several issues related to Docker and permissions. Here’s a summary of the main challenges:

1. **Docker Command Not Found**: 
   When attempting to run the Docker container, I received the error that the `docker` command was not found. I resolved this by installing Docker using the following command:

   ```bash
   sudo apt update
   sudo apt install docker.io 
   sudo systemctl enable docker
   sudo systemctl start docker


After successfully installing Docker, I was able to run RabbitMQ using the following command:
This command started the RabbitMQ server with the management plugin enabled, and it executed correctly, as shown in the attached image.

```bash
sudo docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management


