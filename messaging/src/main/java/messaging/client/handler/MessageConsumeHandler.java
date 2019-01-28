package messaging.client.handler;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import messaging.client.config.MessageClientConfig;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MessageConsumeHandler<T> extends MessageHandler {

    protected final String queue;
    private final Class<T> entityClass;

    private static Logger LOGGER = Logger.getLogger(MessageConsumeHandler.class.getName());

    protected MessageConsumeHandler(MessageClientConfig clientConfig, String queue) {
        super(clientConfig);
        this.queue = queue;

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    protected void init() throws IOException, TimeoutException {
        try (Connection connection = getConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
            channel.queueDeclare(this.queue, true, false, false, null);
            channel.queueBind(this.queue, EXCHANGE_NAME, this.queue);


            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                T data = OBJECT_MAPPER.readValue(delivery.getBody(), this.entityClass);
                if (data != null) {
                    consume(data);
                } else {
                    LOGGER.log(Level.SEVERE, "Empty message.. queue: {0}", this.queue);
                }
            };

            channel.basicConsume(this.queue, Boolean.TRUE, deliverCallback, consumerTag -> {
            });
        }
    }

    protected abstract void consume(T data);

}
