package com.revature.ers.utils;

import java.io.FileReader;
import java.io.IOException;
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
}
