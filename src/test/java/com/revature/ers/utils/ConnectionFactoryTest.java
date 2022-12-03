package com.revature.ers.utils;

import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;

public class ConnectionFactoryTest {
    private static ConnectionFactory sut;

    @Test
    public void test_getInstance() {
        // Arrange
        sut = null;

        // Act
        ConnectionFactory condition = sut.getInstance();

        // Assert
        assertNotNull(condition);
    }

    @Test
    public void test_getConnection() throws SQLException {
        // Arrange

        // Act
        Connection condition = sut.getInstance().getConnection();

        // Assert
        assertNotNull(condition);
    }
}