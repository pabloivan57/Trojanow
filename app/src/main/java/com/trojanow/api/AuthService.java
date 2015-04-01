package com.trojanow.api;
import com.trojanow.model.User;


/**
 * Created by pabloivan57 on 3/27/15.
 */
public class AuthService {
    /**
     * @param user
     * Signup function, it sends a request to API signup backend to create a new user
     * **/
    public void signUp(User user) {

    }

    /**
     *
     * @param email
     * @param password
     * @return user
     *
     * Sends a login request to the server, if successful it returns a User object with the logged
     * user info, otherwise it returns null
     */
    public String login(String email, String password) {
        return null;
    }

    /**
     *
     * @param auth_token
     *
     * Sets the auth_token header on the networking library for any subsequent authenticated requests
     */
    public void setAuthToken(String auth_token) {

    }

}
