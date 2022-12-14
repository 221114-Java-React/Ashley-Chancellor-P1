package com.revature.ers.services;

import com.revature.ers.daos.UserDAO;
import com.revature.ers.dtos.requests.NewLoginRequest;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.User;
import com.revature.ers.utils.custom_exceptions.InvalidAuthException;

import java.util.List;
import java.util.UUID;

// purpose: validate & retrieve user data from DAO
// essentially an API
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User signup(NewUserRequest req) {
        User createdUser = new User(UUID.randomUUID().toString(), req.getUsername(), req.getEmail(),
            req.getPassword1(), req.getGivenName(), req.getSurname(), false,
                "05836bdd-83c4-4ecb-a255-c7f1f7e0bd40"); // EMPLOYEE

        userDAO.save(createdUser);
        return createdUser;
    }

    public User setPassword(String id, String password) {
        User user = userDAO.findById(id);

        if(user == null)
            return null;

        user.setPassword(password);
        userDAO.update(user);
        return user;
    }

    public User setActive(String id) {
        User user = userDAO.findById(id);

        if(user == null)
            return null;

        if(user.isActive())
            user.setActive(false);
        else
            user.setActive(true);

        userDAO.update(user);
        return user;
    }

    public User setRoleId(String id, String roleId) {
        User user = userDAO.findById(id);

        if(user == null)
            return null;

        user.setRoleId(roleId);
        userDAO.update(user);
        return user;
    }

    public Principal login(NewLoginRequest req) {
        User validUser = userDAO.findByUsernameAndPassword(req.getUsername(), req.getPassword());

        if(validUser == null)
            throw new InvalidAuthException("Invalid username or password");

        return new Principal(validUser.getId(), validUser.getUsername(), validUser.getEmail(),
                validUser.getGivenName(), validUser.getSurname(), validUser.isActive(), validUser.getRoleId());
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public List<User> getAllUsersByUsername(String username) {
        return userDAO.findAllByUsername(username);
    }

    // helper functions
    public boolean isValidUsername(String username) {
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
        List<String> emails = userDAO.findAllEmails();
        return emails.contains(email);
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
