package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.User;
import com.revature.ers.models.UserRole;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.utils.custom_exceptions.InvalidAuthException;
import com.revature.ers.utils.custom_exceptions.InvalidUserException;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.List;

// purpose: handle http verbs & endpoints
// hierarchy dependency injection: userHandler -> userService -> userDAO
public class UserHandler {
    private final UserService userService;
    private final TokenService tokenService;
    private final ObjectMapper mapper;

    public UserHandler(UserService userService, TokenService tokenService, ObjectMapper mapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public void signup(Context ctx) throws IOException {
        NewUserRequest req = mapper.readValue(ctx.req.getInputStream(), NewUserRequest.class);

        try {
            userService.signup(req);
            ctx.status(201); // CREATED
        } catch(InvalidUserException e) {
            ctx.status(403); // FORBIDDEN
            ctx.json(e);
        }
    }

    public void getAllUsers(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException();

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            if(!principal.getUserRole().equals(UserRole.ADMIN))
                throw new InvalidAuthException("You are not authorized to do this");

            List<User> users = userService.getAllUsers();
            ctx.json(users);
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        }
    }

    public void getAllUsersByUsername(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            if(!principal.getUserRole().equals(UserRole.ADMIN))
                throw new InvalidAuthException("You are not authorized to do this");

            String username = ctx.req.getParameter("username");
            List<User> users = userService.getAllUsersByUsername(username);
            ctx.json(users);
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        }
    }
}
