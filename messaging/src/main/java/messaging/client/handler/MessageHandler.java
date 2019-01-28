package messaging.client.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.NonNull;
import messaging.client.config.MessageClientConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
abstract class MessageHandler {

    static final String EXCHANGE_NAME = "amq.direct";

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    protected final MessageClientConfig clientConfig;

    MessageHandler(@NonNull MessageClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    private ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.clientConfig.getHost());
        factory.setVirtualHost(this.clientConfig.getVirtualHost());
        factory.setPort(this.clientConfig.getPort());
        factory.setUsername(this.clientConfig.getUsername());
        factory.setPassword(this.clientConfig.getPassword());
        factory.setConnectionTimeout(this.clientConfig.getConnectionTimeout());
        factory.setAutomaticRecoveryEnabled(Boolean.TRUE);
        factory.setTopologyRecoveryEnabled(Boolean.TRUE);
        return factory;
    }

    Connection getConnection() throws IOException, TimeoutException {
        return connectionFactory().newConnection();
    }
}
