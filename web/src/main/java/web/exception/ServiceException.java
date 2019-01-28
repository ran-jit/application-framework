package web.exception;

import lombok.Getter;
import lombok.Setter;
import web.status.StatusCode;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Getter
@Setter
public class ServiceException extends Exception {
    private static final long serialVersionUID = 491797655078136569L;

    private final StatusCode statusCode;

    public ServiceException(StatusCode statusCode) {
        this(statusCode, null, true, false);
    }

    public ServiceException(StatusCode statusCode, Throwable cause) {
        this(statusCode, cause, true, false);
    }

    public ServiceException(StatusCode statusCode, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(statusCode.getMessage(), cause, enableSuppression, writableStackTrace);
        this.statusCode = statusCode;
    }
}
