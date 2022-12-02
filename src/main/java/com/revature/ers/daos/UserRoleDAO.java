package com.revature.ers.daos;

import com.revature.ers.models.User;
import com.revature.ers.models.UserRole;
import com.revature.ers.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO implements CrudDAO<UserRole> {

    @Override
    public void save(UserRole obj) {

    }

    @Override
    public void delete(UserRole obj) {

    }

    @Override
    public void update(UserRole obj) {

    }

    @Override
    public UserRole findById(String id) {
        UserRole userRole = null;

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_user_roles WHERE role_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                userRole = new UserRole(rs.getString("role_id"), rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userRole;
    }

    @Override
    public List<UserRole> findAll() {
        List<UserRole> userRoles = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                UserRole currentUserRole = new UserRole(rs.getString("role_id"), rs.getString("role"));
                userRoles.add(currentUserRole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userRoles;
    }

    // custom methods
    public String findRoleById(String id) {
        String role = null;

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT (role) FROM ers_user_roles WHERE role_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                role = rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }
}
