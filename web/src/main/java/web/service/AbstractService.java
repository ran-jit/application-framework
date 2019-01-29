package web.service;

import web.status.Status;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    protected Response sendResponse(Object data) {
        web.response.Response response = web.response.Response.builder()
                .status(Status.builder()
                        .success(Boolean.TRUE)
                        .type(Status.Type.SUCCESS)
                        .build())
                .data(data)
                .build();

        return Response.status(Response.Status.OK)
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
