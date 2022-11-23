package com.revature.ers.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.daos.UserDAO;
import com.revature.ers.handlers.UserHandler;
import com.revature.ers.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Router {
    public static void router(Javalin app) {
        ObjectMapper mapper = new ObjectMapper();

        // User
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserHandler userHandler = new UserHandler(userService, mapper);

        // handler groups
        // routes -> handler -> service -> dao
        app.routes(() -> {
            path("/users", () -> {
                post(ctx -> userHandler.signup(ctx));
            });
        });
    }
}
