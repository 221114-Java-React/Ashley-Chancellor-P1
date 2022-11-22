package com.revature.ers.utils;

import com.revature.ers.daos.UserDAO;
import com.revature.ers.handlers.UserHandler;
import com.revature.ers.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Router {
    public static void router(Javalin app) {

        // User
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserHandler userHandler = new UserHandler(userService);

        // handler groups
        // routes -> handler -> service -> dao
        app.routes(() -> {
            path("/users", () -> {
                post(c -> userHandler.signup(c));
            });
        });
    }
}
