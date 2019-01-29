package identity.manager;

import identity.dao.UserDAO;
import identity.model.UserRequest;

public class AuthManager {

    private final UserDAO userDAO;

    public AuthManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void checkRegistered(UserRequest request) {

    }

    public void login(UserRequest request) {

    }

    public void logout(String accessToken) {

    }

}
