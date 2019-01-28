package web.ext;

import web.exception.ServiceException;
import web.status.Status;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * author: Ranjith Manickam @ 28 Jan' 2019.
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException ex) {
        web.response.Response response = web.response.Response.builder()
                .status(Status.builder()
                        .statusCode(ex.getStatusCode())
                        .success(Boolean.FALSE)
                        .type(Status.Type.ERROR)
                        .build())
                .build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .type(MediaType.APPLICATION_JSON).build();
    }
}
