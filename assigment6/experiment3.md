# Experiment 3: Work Queues with RabbitMQ

In this experiment, we implemented two Java classes, `NewTask` and `Worker`, to demonstrate the use of work queues using RabbitMQ. Work queues allow the distribution of time-consuming tasks among multiple workers.

## NewTask Class
The `NewTask` class is responsible for sending messages to a work queue in RabbitMQ. The key points of the implementation are as follows:

- **Queue Declaration**: The class declares a queue named "task_queue" using the `queueDeclare` method.
- **Message Sending**: It publishes a message to the declared queue with persistence properties.

Here’s the code for the `NewTask` class:

```java
package dat250;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            String message = String.join(" ", argv);

            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}

```
Worker Class
The Worker class is responsible for receiving and processing messages from the "task_queue". The key points of the implementation are as follows:

Queue Declaration: Similar to the NewTask class, it declares the "task_queue".
Message Consumption: The class consumes messages from the queue, simulates work by sleeping for one second per dot in the message, and acknowledges the message once processed.
Here’s the code for the Worker class:

```java
package dat250;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Worker {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    final Connection connection = factory.newConnection();
    final Channel channel = connection.createChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    channel.basicQos(1);

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");

        System.out.println(" [x] Received '" + message + "'");
        try {
            doWork(message);
        } finally {
            System.out.println(" [x] Done");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    };
    channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
  }

  private static void doWork(String task) {
    for (char ch : task.toCharArray()) {
        if (ch == '.') {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException _ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }
  }
}
````
Dependencies Added
To support the RabbitMQ functionality, the following dependencies were added to the project:

```java
implementation("com.rabbitmq:amqp-client:5.16.0")
implementation("org.slf4j:slf4j-api:1.7.36")
implementation("org.slf4j:slf4j-simple:1.7.36")

````

Screenshot:

![Captura de pantalla 2024-10-06 182401](https://github.com/user-attachments/assets/e8026a8f-6923-4f1e-8c21-1534db62628e)
