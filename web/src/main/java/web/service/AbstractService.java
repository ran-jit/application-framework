package web.service;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractService {

    private final Logger logger;

    public AbstractService(Logger logger) {
        this.logger = logger;
    }

    public void logException(String message, Object... objects) {
        this.logger.log(Level.SEVERE, message, objects);
    }
}
