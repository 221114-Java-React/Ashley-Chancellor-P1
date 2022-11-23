package com.revature.ers.daos;

import com.revature.ers.models.User;
import com.revature.ers.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// purpose: return data from db
public class UserDAO implements CrudDAO<User> {

    @Override
    public void save(User obj) {

    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public void update(User obj) {

    }

    @Override
    public User findByID() {
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User currentUser = new User(rs.getString("user_id"), rs.getString("username"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("given_name"), rs.getString("surname"),
                        rs.getBoolean("is_active"), rs.getString("role_id"));
                users.add(currentUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // custom methods
    public List<String> findAllUsernames() {
        List<String> usernames = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT (username) FROM ers_users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String currentUsername = rs.getString("username");
                usernames.add(currentUsername);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usernames;
    }
}
