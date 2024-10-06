# Experiment 4: RabbitMQ Publish/Subscribe Tutorial

In this experiment, we implemented a simple logging system using the publish/subscribe pattern in RabbitMQ. This system consists of two programs: one that emits log messages (`EmitLog`) and another that receives and prints them (`ReceiveLogs`). The publish/subscribe pattern allows messages to be delivered to multiple consumers simultaneously.

## EmitLog Class
The `EmitLog` class is responsible for sending log messages to the "logs" exchange.

Here’s the code for the `EmitLog` class:

```java
package dat250;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = argv.length < 1 ? "info: Hello World!" : String.join(" ", argv);

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}

````
ReceiveLogs Class
The ReceiveLogs class is responsible for receiving and printing log messages from the "logs" exchange.

Here’s the code for the ReceiveLogs class:

```java
package dat250;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogs {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
````

Screenshot:

![Captura de pantalla 2024-10-06 201652](https://github.com/user-attachments/assets/3233b533-74cd-408e-bd95-37a53717c52c)
