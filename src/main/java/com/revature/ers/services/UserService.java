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
        User createdUser = new User(UUID.randomUUID().toString(), req.getUsername(), req.getEmail(),
            req.getPassword1(), req.getGivenName(), req.getSurname(), true, UserRole.EMPLOYEE);

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
    }

    public boolean isDuplicateUsername(String username) {
        List<String> usernames = userDAO.findAllUsernames();
        return usernames.contains(username);
    }

    public boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$");
    }

    public boolean isDuplicateEmail(String email) {
        List<String> usernames = userDAO.findAllEmails();
        return usernames.contains(email);
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public boolean isSamePassword(String password1, String password2) {
        return password1.equals(password2);
    }

    public boolean isValidName(String givenName, String surname) {
        return !givenName.isEmpty() && !surname.isEmpty();
    }
}
