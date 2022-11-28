package com.revature.ers.dtos.responses;

import com.revature.ers.models.UserRole;

public class Principal {
    private String userId;
    private String username;
    private UserRole userRole;

    public Principal() {
        super();
    }

    public Principal(String userId, String username, UserRole userRole) {
        this.userId = userId;
        this.username = username;
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
