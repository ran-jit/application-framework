package web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import web.status.StatusCode;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@AllArgsConstructor
public class AuthenticationException extends Exception {
    private static final long serialVersionUID = -561004719041254691L;

    private final StatusCode statusCode;

    public interface AuthenticationFailiureStatus {
        StatusCode USER_NOT_EXISTS = StatusCode.builder().code("A-1011").build();
        StatusCode INVALID_CREDENTIALS = StatusCode.builder().code("A-1012").build();
        StatusCode INVALID_ACCESS_TOKEN = StatusCode.builder().code("A-1013").build();
        StatusCode ACCESS_TOKEN_EXPIRED = StatusCode.builder().code("A-1014").build();
        StatusCode INVALID_REFRESH_TOKEN = StatusCode.builder().code("A-1015").build();
        StatusCode REFRESH_TOKEN_EXPIRED = StatusCode.builder().code("A-1016").build();
    }
}
