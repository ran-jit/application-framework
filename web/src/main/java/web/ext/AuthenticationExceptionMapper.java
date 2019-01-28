package web.ext;

import web.exception.AuthenticationException;
import web.status.Status;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    @Override
    public Response toResponse(AuthenticationException ex) {
        web.response.Response response = web.response.Response.builder()
                .status(Status.builder()
                        .statusCode(ex.getStatusCode())
                        .success(Boolean.FALSE)
                        .type(Status.Type.ERROR)
                        .build())
                .build();

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(response)
                .type(MediaType.APPLICATION_JSON).build();
    }
}
