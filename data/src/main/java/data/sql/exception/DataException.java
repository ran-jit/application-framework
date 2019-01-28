package data.sql.exception;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class DataException extends Exception {
    private static final long serialVersionUID = 417308628898567848L;

    public DataException(String message) {
        this(message, null, true, false);
    }

    public DataException(String message, Throwable cause) {
        this(message, cause, true, false);
    }

    public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
