package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.services.UserService;
import com.revature.ers.utils.custom_exceptions.InvalidUserException;
import io.javalin.http.Context;

import java.io.IOException;

// purpose: handle http verbs & endpoints
// hierarchy dependency injection: userHandler -> userService -> userDAO
public class UserHandler {
    private final UserService userService;
    private final ObjectMapper mapper;

    public UserHandler(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    public void signup(Context ctx) throws IOException {
        NewUserRequest req = mapper.readValue(ctx.req.getInputStream(), NewUserRequest.class);

        try {
            userService.saveUser(req);
            ctx.status(201); // CREATED
        } catch(InvalidUserException e) {
            ctx.status(403); // FORBIDDEN
            ctx.json(e);
        }
    }
}
