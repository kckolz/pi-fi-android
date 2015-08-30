package pack.wolf.com.pifi.service;


import pack.wolf.com.pifi.service.api.AuthenticationService;
import pack.wolf.com.pifi.service.impl.AuthenticationServiceImpl;

/**
 * Created by ryanmoore on 6/8/15.
 */
public class AuthenticationServiceFactory {

    private static AuthenticationService authenticationService;

    public static AuthenticationService getInstance() {

        if (authenticationService == null) {
            authenticationService = new AuthenticationServiceImpl();
        }

        return authenticationService;
    }
}
