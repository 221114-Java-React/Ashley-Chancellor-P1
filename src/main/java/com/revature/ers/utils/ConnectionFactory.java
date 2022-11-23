package com.revature.ers.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// purpose: bridge DAO classes with db
// singleton design pattern
public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;

    // load in jdbc
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // class to read properties file
    private final Properties props = new Properties();

    private ConnectionFactory() {
        try {
            props.load(new FileReader("src/main/resources/db.properties"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // static method that controls access to singleton instance
    public static ConnectionFactory getInstance() {
        // ensure that instance hasn't yet been initialized
        if(connectionFactory == null)
            connectionFactory = new ConnectionFactory();

        return connectionFactory;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"),
                props.getProperty("password"));

        if(conn == null)
            throw new RuntimeException("Could not establish connection with the database");

        return conn;
    }
}
