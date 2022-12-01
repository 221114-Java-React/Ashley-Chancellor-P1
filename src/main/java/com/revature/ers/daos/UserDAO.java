package com.revature.ers.daos;

import com.revature.ers.models.User;
import com.revature.ers.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// purpose: return user data from db
public class UserDAO implements CrudDAO<User> {

    @Override
    public void save(User obj) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ers_users (user_id, username, email, " +
                    "password, given_name, surname, is_active, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getGivenName());
            ps.setString(6, obj.getSurname());
            ps.setBoolean(7, obj.isActive());
            ps.setString(8, obj.getRoleId());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public void update(User obj) {

    }

    @Override
    public User findByID(String id) {
        User user = null;

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_users WHERE user_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                user = new User(rs.getString("user_id"), rs.getString("username"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("given_name"), rs.getString("surname"),
                        rs.getBoolean("is_active"), rs.getString("role_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
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

    public List<String> findAllEmails() {
        List<String> emails = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT (email) FROM ers_users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String currentEmail = rs.getString("email");
                emails.add(currentEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emails;
    }

    public User findByUsernameAndPassword(String username, String password) {
        User user = null;

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                user = new User(rs.getString("user_id"), rs.getString("username"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("given_name"), rs.getString("surname"),
                        rs.getBoolean("is_active"), rs.getString("role_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> findAllByUsername(String username) {
        List<User> users = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_users WHERE username LIKE ?");
            ps.setString(1, username + "%");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User user = new User(rs.getString("user_id"), rs.getString("username"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("given_name"), rs.getString("surname"),
                        rs.getBoolean("is_active"), rs.getString("role_id"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
