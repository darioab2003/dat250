## Experiment 2: Sending and Receiving Messages with RabbitMQ

In this experiment, we implemented two Java classes, `Send` and `Recv`, to demonstrate sending and receiving messages using RabbitMQ.

### Send Class

The `Send` class is responsible for sending messages to a RabbitMQ queue. The key points of the implementation are as follows:

- **Queue Declaration**: The class declares a queue named "hello" using the `queueDeclare` method.
- **Message Sending**: It publishes a message ("Hello World!") to the declared queue.

Here’s the code for the `Send` class:

```java
package dat250;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
````

Recv Class
The Recv class is responsible for receiving messages from the "hello" queue. The key points of the implementation are as follows:

Queue Declaration: Similar to the Send class, it declares the "hello" queue.
Message Consumption: The class consumes messages from the queue and prints them when received.

Here’s the code for the `Recv` class:

```java

package dat250;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
````

Dependencies Added
To support the RabbitMQ functionality, the following dependencies were added to the project:

implementation ("com.rabbitmq:amqp-client:5.16.0")
implementation ("org.slf4j:slf4j-api:1.7.36")
implementation ("org.slf4j:slf4j-simple:1.7.36")



Run the Send class in another terminal to send a message:
![Captura de pantalla 2024-10-04 182627](https://github.com/user-attachments/assets/d4d70eca-b292-4efc-b83c-6c572d968143)


Run the Recv class in one terminal to start listening for messages:

![Captura de pantalla 2024-10-04 182808](https://github.com/user-attachments/assets/b2b09af9-028f-4766-b63c-0d594020a90e)


