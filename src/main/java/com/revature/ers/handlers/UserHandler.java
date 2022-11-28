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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

// purpose: handle http verbs & endpoints
// hierarchy dependency injection: userHandler -> userService -> userDAO
public class UserHandler {
    private final UserService userService;
    private final TokenService tokenService;
    private final ObjectMapper mapper;

    private final static Logger logger = LoggerFactory.getLogger(User.class);

    public UserHandler(UserService userService, TokenService tokenService, ObjectMapper mapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public void signup(Context ctx) throws IOException {
        NewUserRequest req = mapper.readValue(ctx.req.getInputStream(), NewUserRequest.class);

        try {
            logger.info("Attempting to sign up...");

            User createdUser;

            if(userService.isValidUserName(req.getUsername())) {
                if (!userService.isDuplicateUsername(req.getUsername())) {
                    if (userService.isValidPassword(req.getPassword1())) {
                        if (userService.isSamePassword(req.getPassword1(), req.getPassword2()))
                            createdUser = userService.signup(req);
                        else
                            throw new InvalidUserException("Passwords do not match");
                    } else
                        throw new InvalidUserException("Password" +
                                "/n/t * must be at least 8 and no more than 20 characters long" +
                                "/n/t * must contain at least 1 number" +
                                "/n/t * must contain at least 1 uppercase letter" +
                                "/n/t * must contain at least 1 lowercase letter" +
                                "/n/t * must contain at least one special character (!@#$%&*()-+=^)" +
                                "/n/t * may not contain any white space");
                } else
                    throw new InvalidUserException("Username is already taken");
            } else
                throw new InvalidUserException("Username" +
                        "/n/t * must be at least 8 and no more than 20 characters long" +
                        "/n/t * must contain only alphanumeric characters or . or _" +
                        "/n/t * may not contain _ or . at the beginning or end" +
                        "/n/t * may not contain __ or _. or ._ or ..");

            ctx.status(201); // CREATED
            ctx.json(createdUser.getId());
            logger.info("Signup attempt successful");
        } catch(InvalidUserException e) {
            ctx.status(403); // FORBIDDEN
            ctx.json(e);
            logger.info("Signup attempt unsuccessful");
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
