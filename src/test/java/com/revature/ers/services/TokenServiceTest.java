package com.revature.ers.services;

import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.utils.JwtConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.Assert.*;

public class TokenServiceTest {
    private TokenService sut;
    private final JwtConfig mockJwtConfig = Mockito.mock(JwtConfig.class);

    @Before
    public void init() {
        sut = new TokenService(mockJwtConfig);
    }

    @Test
    public void test_generateToken_givenSubject() {
        // Arrange
        Principal subject = new Principal(UUID.randomUUID().toString(), "ScruffyC",
                "scruffy.chancellor@icloud.com", "Ashley", "Chancellor", true,
                "");

        // Act

        // Assert
    }

    /*@Test
    public void test_extractRequesterDetails_returnPrincipalGivenToken() {
        // Arrange
        String token = sut.generateToken();

        // Act
        Principal condition = sut.extractRequesterDetails(token);

        // Assert
    }

    @Test
    public void test_extractRequesterDetails_returnNullGivenToken() {
        // Arrange
        String token;

        // Act
        Principal condition = sut.extractRequesterDetails(token);

        // Assert
    }*/
}