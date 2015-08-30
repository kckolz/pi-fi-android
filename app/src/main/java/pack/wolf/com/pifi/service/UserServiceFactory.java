package pack.wolf.com.pifi.service;


import pack.wolf.com.pifi.service.api.UserService;
import pack.wolf.com.pifi.service.impl.UserServiceImpl;

public class UserServiceFactory {

    private static UserService userService;

    public static UserService getInstance() {

        if (userService == null) {
            userService = new UserServiceImpl();
        }

        return userService;
    }
}
