package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.NewLoginRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.User;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.utils.custom_exceptions.InvalidAuthException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// purpose: authenticate user
public class AuthHandler {
    // dependency injections
    private final UserService userService;
    private final TokenService tokenService;
    private final ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    public AuthHandler(UserService userService, TokenService tokenService, ObjectMapper mapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public void authenticateUser(Context ctx) throws IOException {
        NewLoginRequest req = mapper.readValue(ctx.req.getInputStream(), NewLoginRequest.class);
        logger.info("Attempting to log in...");

        try {
            Principal principal = userService.login(req);

            // check if user account is active
            if(!principal.isActive())
                throw new InvalidAuthException("Your account is not active");

            // generate token from Principal obj
            String token = tokenService.generateToken(principal);

            // set header with auth token
            ctx.res.setHeader("authorization", token);

            // return principal obj as json
            ctx.json(principal);

            ctx.status(202); // ACCEPTED

            logger.info("Login successful");
        } catch(InvalidAuthException e){
            ctx.status(401);
            ctx.json(e);
        }
    }
}
