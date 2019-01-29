package data.sql.exception;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public class DataException extends Exception {

    private static final long serialVersionUID = 8657231549609258003L;

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    protected DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
