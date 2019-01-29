package web.service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
public abstract class AbstractService {

    private final Logger logger;

    public AbstractService(Logger logger) {
        this.logger = logger;
    }

    protected void logException(String message, Object... objects) {
        this.logger.log(Level.SEVERE, message, objects);
    }
}
