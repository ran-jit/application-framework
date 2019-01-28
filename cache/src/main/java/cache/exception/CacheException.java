package cache.exception;

public class CacheException extends Exception {
    private static final long serialVersionUID = -4204987455483426266L;

    public CacheException(String message) {
        this(message, null, true, false);
    }

    public CacheException(String message, Throwable cause) {
        this(message, cause, true, false);
    }

    public CacheException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
