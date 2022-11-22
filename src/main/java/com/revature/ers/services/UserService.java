package com.revature.ers.services;

import com.revature.ers.daos.UserDAO;

// purpose: validate & retrieve data from DAO
// essentially an API
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
