package com.revature.ers.services;

import com.revature.ers.daos.UserDAO;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.models.User;
import com.revature.ers.models.UserRole;
import com.revature.ers.utils.custom_exceptions.InvalidUserException;

import java.util.List;
import java.util.UUID;

// purpose: validate & retrieve data from DAO
// essentially an API
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void saveUser(NewUserRequest req) {
        List<String> usernames = userDAO.findAllUsernames();

        if(!isValidUserName(req.getUsername()))
            throw new InvalidUserException("Username" +
                    "/n/t * must be at least 8 and no more than 20 characters long" +
                    "/n/t * must contain only alphanumeric characters or . or _" +
                    "/n/t * may not contain _ or . at the beginning or end" +
                    "/n/t * may not contain __ or _. or ._ or ..");

        if(usernames.contains(req.getUsername()))
            throw new InvalidUserException("Username is already taken");

        if(!isValidPassword(req.getPassword1()))
            throw new InvalidUserException("Password" +
                    "/n/t * must be at least 8 and no more than 20 characters long" +
                    "/n/t * must contain at least 1 number" +
                    "/n/t * must contain at least 1 uppercase letter" +
                    "/n/t * must contain at least 1 lowercase letter" +
                    "/n/t * must contain at least one special character (!@#$%&*()-+=^)" +
                    "/n/t * may not contain any white space");

        if(!req.getPassword1().equals(req.getPassword2()))
            throw new InvalidUserException("Passwords do not match");

        User createdUser = new User(UUID.randomUUID().toString(), req.getUsername(), "email@email.com", req.getPassword1(), "John", "Smith", true, UserRole.EMPLOYEE);
        userDAO.save(createdUser);
    }

    // helper functions
    private boolean isValidUserName(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");

        /* A username is considered valid if all the following constraints are satisfied:
             * It contains at least 8 characters and at most 20 characters.
             * It doesn't contain a '_' or '.' at the beginning.
             * It doesn't contain a "__" or "_." or "._" or "..".
             * It contains only alphanumeric characters or '.' or '_'.
             * It doesn't contain '_' or '.' at the end.
         */
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8, 20}$");

        /* A password is considered valid if all the following constraints are satisfied:
             * It contains at least 8 characters and at most 20 characters.
             * It contains at least one digit.
             * It contains at least one upper case alphabet.
             * It contains at least one lower case alphabet.
             * It contains at least one special character which includes !@#$%&*()-+=^.
             * It doesn't contain any white space.
         */
    }
}
