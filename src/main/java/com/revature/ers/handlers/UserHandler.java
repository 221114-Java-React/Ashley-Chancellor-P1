package com.revature.ers.handlers;

import com.revature.ers.services.UserService;
import io.javalin.http.Context;

import java.io.IOException;

// purpose: handle http verbs & endpoints
// hierarchy dependency injection: userHandler -> userService -> userDAO
public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void signup(Context c) {

    }
}
