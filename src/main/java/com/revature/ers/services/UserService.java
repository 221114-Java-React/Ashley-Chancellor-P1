package com.revature.ers.services;

import com.revature.ers.daos.UserDAO;
import com.revature.ers.dtos.requests.NewLoginRequest;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.User;
import com.revature.ers.models.UserRole;
import com.revature.ers.utils.custom_exceptions.InvalidAuthException;

import java.util.List;
import java.util.UUID;

// purpose: validate & retrieve data from DAO
// essentially an API
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User signup(NewUserRequest req) {
        User createdUser = new User(UUID.randomUUID().toString(), req.getUsername(), "email@email.com",
            req.getPassword1(), "John", "Smith", true, UserRole.EMPLOYEE);

        userDAO.save(createdUser);
        return createdUser;
    }

    public Principal login(NewLoginRequest req) {
        User validUser = userDAO.findByUsernameAndPassword(req.getUsername(), req.getPassword());

        if(validUser == null)
            throw new InvalidAuthException("Invalid username or password");

        return new Principal(validUser.getId(), validUser.getUsername(), validUser.getRole());
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public List<User> getAllUsersByUsername(String username) {
        return userDAO.findAllByUsername(username);
    }

    // helper functions
    public boolean isValidUserName(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");

        /* A username is considered valid if all the following constraints are satisfied:
             * It contains at least 8 characters and at most 20 characters.
             * It doesn't contain a '_' or '.' at the beginning.
             * It doesn't contain a "__" or "_." or "._" or "..".
             * It contains only alphanumeric characters or '.' or '_'.
             * It doesn't contain '_' or '.' at the end.
         */
    }

    public boolean isDuplicateUsername(String username) {
        List<String> usernames = userDAO.findAllUsernames();
        return usernames.contains(username);
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$");

        /* A password is considered valid if all the following constraints are satisfied:
             * It contains at least 8 characters and at most 20 characters.
             * It contains at least one digit.
             * It contains at least one upper case alphabet.
             * It contains at least one lower case alphabet.
             * It contains at least one special character which includes !@#$%&*()-+=^.
             * It doesn't contain any white space.
         */
    }

    public boolean isSamePassword(String password1, String password2) {
        return password1.equals(password2);
    }
}
