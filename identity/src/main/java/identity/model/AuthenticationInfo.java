package identity.model;

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
public class AuthenticationInfo implements Serializable {
    private static final long serialVersionUID = -8167022842240869634L;

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private Type type;

    private Long userId;

    public enum Type {
        BEARER;
    }
}
