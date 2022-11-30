package com.revature.ers.models;

public class UserRole {
    private String id;
    private Role role;

    public UserRole () {
        super();
    }

    public UserRole(String id, Role role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id='" + id + '\'' +
                ", role=" + role +
                '}';
    }
}

enum Role {
    EMPLOYEE, FINANCE_MANAGER, ADMIN
}