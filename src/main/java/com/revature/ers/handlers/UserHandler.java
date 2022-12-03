package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.User;
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

            if(userService.isValidUsername(req.getUsername())) {
                if(!userService.isDuplicateUsername(req.getUsername())) {
                    if(userService.isValidEmail(req.getEmail())) {
                        if (!userService.isDuplicateEmail(req.getEmail())) {
                            if (userService.isValidPassword(req.getPassword1())) {
                                if (userService.isSamePassword(req.getPassword1(), req.getPassword2())) {
                                    if (userService.isValidName(req.getGivenName(), req.getSurname()))
                                        createdUser = userService.signup(req);
                                    else
                                        throw new InvalidUserException("Please enter a given name and surname");
                                } else
                                    throw new InvalidUserException("Passwords do not match");
                            } else
                                throw new InvalidUserException("Password must be at least 8 characters long, and " +
                                        "contain at least one number");
                        } else
                            throw new InvalidUserException("Email address is already taken by another user");
                    } else
                        throw new InvalidUserException("Please enter a valid email address");
                } else
                    throw new InvalidUserException("Username is already taken");
            } else
                throw new InvalidUserException("Username must be 8-20 characters long");

            ctx.status(201); // CREATED
            ctx.json(createdUser);
            logger.info("Signup attempt successful");
        } catch(InvalidUserException e) {
            ctx.status(403); // FORBIDDEN
            ctx.json(e);
            logger.info("Signup attempt unsuccessful");
        }
    }

    public void resetPassword(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if (token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if (principal == null)
                throw new InvalidAuthException("Invalid token");

            if (!principal.getRoleId().equals("53069ab4-c085-47d5-9d0d-aafb6c3b475a")) // ADMIN
                throw new InvalidAuthException("You are not authorized to do this");

            String id = ctx.req.getParameter("id");
            User user = userService.setPassword(id, "passw0rd");

            if(user == null)
                throw new InvalidUserException("User not found");

            ctx.json(user);
            logger.info("User password has been reset");
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        } catch(InvalidUserException e) {
            ctx.status(404); // NOT FOUND
            ctx.json(e);
        }
    }

    public void setActive(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if (token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if (principal == null)
                throw new InvalidAuthException("Invalid token");

            if (!principal.getRoleId().equals("53069ab4-c085-47d5-9d0d-aafb6c3b475a")) // ADMIN
                throw new InvalidAuthException("You are not authorized to do this");

            String id = ctx.req.getParameter("id");
            User user = userService.setActive(id);

            if(user == null)
                throw new InvalidUserException("User not found");

            ctx.json(user);
            logger.info("User active status has been changed");
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        } catch(InvalidUserException e) {
            ctx.status(404); // NOT FOUND
            ctx.json(e);
        }
    }

    public void setRole(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if (token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if (principal == null)
                throw new InvalidAuthException("Invalid token");

            if (!principal.getRoleId().equals("53069ab4-c085-47d5-9d0d-aafb6c3b475a")) // ADMIN
                throw new InvalidAuthException("You are not authorized to do this");

            String id = ctx.req.getParameter("id");
            String roleId = ctx.req.getParameter("roleId");
            User user = userService.setRoleId(id, roleId);

            if(user == null)
                throw new InvalidUserException("User not found");

            ctx.json(user);
            logger.info("User role set");
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        } catch(InvalidUserException e) {
            ctx.status(404); // NOT FOUND
            ctx.json(e);
        }
    }

    public void getAllUsers(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            if(!principal.getRoleId().equals("53069ab4-c085-47d5-9d0d-aafb6c3b475a")) // ADMIN
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

            if(!principal.getRoleId().equals("53069ab4-c085-47d5-9d0d-aafb6c3b475a")) // ADMIN
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
