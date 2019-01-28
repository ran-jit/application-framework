package messaging.client.config;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@Builder
@ToString
public class MessageClientConfig implements Serializable {
    private static final long serialVersionUID = 4332847982730096438L;

    private String host;
    private Integer port;
    private String virtualHost;
    private String username;
    private String password;
    @Builder.Default
    private Integer connectionTimeout = 3000;
}
