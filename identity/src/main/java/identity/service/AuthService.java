package identity.service;

import identity.manager.AuthManager;
import identity.model.AuthenticationInfo;
import identity.model.UserRequest;
import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/identity")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthService {

    private final AuthManager authManager;

    @Inject
    public AuthService(AuthManager authManager) {
        this.authManager = authManager;
    }

    @POST
    @Path("/check")
    public void checkRegistered(UserRequest request) {
        try {
            authManager.checkRegistered(request);
        } catch (Exception ex) {

        }
    }

    @POST
    @Path("/login")
    public void login(UserRequest request) {
        try {

        } catch (Exception ex) {

        }
    }

    @POST
    @Path("/logout")
    public void logout(String accessToken) {
        try {

        } catch (Exception ex) {

        }
    }

    @POST
    @Path("/token")
    public void token(AuthenticationInfo authenticationInfo) {
        try {

        } catch (Exception ex) {

        }
    }

}
