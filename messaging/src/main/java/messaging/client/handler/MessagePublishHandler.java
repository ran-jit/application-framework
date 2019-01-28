package messaging.client.handler;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import messaging.client.config.MessageClientConfig;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class MessagePublishHandler<T extends Serializable> extends MessageHandler {

    public MessagePublishHandler(MessageClientConfig clientConfig) {
        super(clientConfig);
    }

    public void publishMessage(String queue, T data) throws IOException, TimeoutException {
        try (Connection connection = getConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, Boolean.TRUE);
            channel.basicPublish(EXCHANGE_NAME, queue, null, OBJECT_MAPPER.writeValueAsBytes(data));
        }
    }
}
