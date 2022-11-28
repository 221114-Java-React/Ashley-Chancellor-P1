package com.revature.ers.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.daos.UserDAO;
import com.revature.ers.handlers.AuthHandler;
import com.revature.ers.handlers.UserHandler;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
    public static void router(Javalin app) {
        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);

        // User
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserHandler userHandler = new UserHandler(userService, tokenService, mapper);

        // Auth
        AuthHandler authHandler = new AuthHandler(userService, tokenService, mapper);

        // handler groups
        // routes -> handler -> service -> dao
        app.routes(() -> {
            // User
            path("/users", () -> {
                get(userHandler::getAllUsers);
                get("/name", userHandler::getAllUsersByUsername);
                post(userHandler::signup);
            });

            // Auth
            path("/auth", () -> {
                post(authHandler::authenticateUser);
            });
        });
    }
}
