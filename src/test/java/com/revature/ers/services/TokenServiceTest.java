package com.revature.ers.services;

import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.utils.JwtConfig;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
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
                "53069ab4-c085-47d5-9d0d-aafb6c3b475a");

        SignatureAlgorithm stubbedSigAlg = SignatureAlgorithm.HS256;
        Key stubbedSigningKey = new SecretKeySpec(new byte[1], stubbedSigAlg.getJcaName());

        Mockito.when(mockJwtConfig.getSigAlg()).thenReturn(stubbedSigAlg);
        Mockito.when(mockJwtConfig.getSigningKey()).thenReturn(stubbedSigningKey);

        // Act
        String condition = sut.generateToken(subject);


        // Assert
        assertNotNull(condition);
    }

    @Test
    public void test_extractRequesterDetails_givenToken() {
        // Arrange
        String token = "";

        Key stubbedSigningKey = new SecretKeySpec(new byte[1], SignatureAlgorithm.HS256.getJcaName());

        Mockito.when(mockJwtConfig.getSigningKey()).thenReturn(stubbedSigningKey);

        // Act
        Principal condition = sut.extractRequesterDetails(token);

        // Assert
        assertNull(condition);
    }
}