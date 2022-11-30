package com.revature.ers.services;

import com.revature.ers.daos.UserDAO;
import com.revature.ers.dtos.requests.NewLoginRequest;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.User;
import com.revature.ers.models.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService sut;
    private final UserDAO mockUserDao = Mockito.mock(UserDAO.class);

    @Before
    public void init() {
        sut = new UserService(mockUserDao);
    }

    @Test
    public void test_isValidUsername_givenValidUsername() {
        // Arrange
        String validUsername = "ScruffyC";

        // Act
        boolean condition = sut.isValidUsername(validUsername);

        // Assert
        assertTrue(condition);
    }

    @Test
    public void test_isValidUsername_givenInvalidUsername() {
        // Arrange
        String invalidUsername = "test";

        // Act
        boolean condition = sut.isValidUsername(invalidUsername);

        // Assert
        assertFalse(condition);
    }

    @Test
    public void test_isDuplicateUsername_givenUniqueUsername() {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String uniqueUsername = "ScruffyC";
        List<String> stubbedUsernames = Arrays.asList("tester001", "tester002", "tester003");

        Mockito.when(mockUserDao.findAllUsernames()).thenReturn(stubbedUsernames);

        // Act
        boolean condition = spySut.isDuplicateUsername(uniqueUsername);

        // Assert
        assertFalse(condition);
    }

    @Test
    public void test_isDuplicateUsername_givenDuplicateUsername() {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String duplicateUsername = "tester002";
        List<String> stubbedUsernames = Arrays.asList("tester001", "tester002", "tester003");

        Mockito.when(mockUserDao.findAllUsernames()).thenReturn(stubbedUsernames);

        // Act
        boolean condition = spySut.isDuplicateUsername(duplicateUsername);

        // Assert
        assertTrue(condition);
    }

    @Test
    public void test_isValidEmail_givenValidEmail() {
        // Arrange
        String validEmail = "scruffy.chancellor@icloud.com";

        // Act
        boolean condition = sut.isValidEmail(validEmail);

        // Assert
        assertTrue(condition);
    }

    @Test
    public void test_isValidEmail_givenInvalidEmail() {
        // Arrange
        String invalidEmail = "test";

        // Act
        boolean condition = sut.isValidEmail(invalidEmail);

        // Assert
        assertFalse(condition);
    }

    @Test
    public void test_isDuplicateEmail_givenUniqueEmail() {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String uniqueEmail = "scruffy.chancellor@icloud.com";
        List<String> stubbedEmails = Arrays.asList("tester1@test.com", "tester2@test.com", "tester3@test.com");

        Mockito.when(mockUserDao.findAllEmails()).thenReturn(stubbedEmails);

        // Act
        boolean condition = spySut.isDuplicateEmail(uniqueEmail);

        // Assert
        assertFalse(condition);
    }

    @Test
    public void test_isDuplicateEmail_givenDuplicateEmail() {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String duplicateEmail = "tester3@test.com";
        List<String> stubbedEmails = Arrays.asList("tester1@test.com", "tester2@test.com", "tester3@test.com");

        Mockito.when(mockUserDao.findAllEmails()).thenReturn(stubbedEmails);

        // Act
        boolean condition = spySut.isDuplicateEmail(duplicateEmail);

        // Assert
        assertTrue(condition);
    }

    @Test
    public void test_isValidPassword_givenValidPassword() {
        // Arrange
        String validPassword = "Hazelbean19";

        // Act
        boolean condition = sut.isValidPassword(validPassword);

        // Assert
        assertTrue(condition);
    }

    @Test
    public void test_isValidPassword_givenInvalidPassword() {
        // Arrange
        String invalidPassword = "password";

        // Act
        boolean condition = sut.isValidPassword(invalidPassword);

        // Assert
        assertFalse(condition);
    }

    @Test
    public void test_isSamePassword_givenSamePassword() {
        // Arrange
        String password = "passw0rd";
        String samePassword = "passw0rd";

        // Act
        boolean condition = sut.isSamePassword(password, samePassword);

        // Assert
        assertTrue(condition);
    }

    @Test
    public void test_isSamePassword_givenDiffPassword() {
        // Arrange
        String password = "passw0rd";
        String diffPassword = "Passw0rd";

        // Act
        boolean condition = sut.isSamePassword(password, diffPassword);

        // Assert
        assertFalse(condition);
    }

    @Test
    public void test_isValidName_givenValidName() {
        // Arrange
        String validGivenName = "Ashley";
        String validSurname = "Chancellor";

        // Act
        boolean condition = sut.isValidName(validGivenName, validSurname);

        // Assert
        assertTrue(condition);
    }

    @Test
    public void test_isValidName_givenInvalidName() {
        // Arrange
        String invalidGivenName = "";
        String validSurname = "Smith";

        // Act
        boolean condition = sut.isValidName(invalidGivenName, validSurname);

        // Assert
        assertFalse(condition);
    }

    @Test
    public void test_signup_persistUserGivenValidUsernameEmailPasswordAndName() {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String validUsername = "tester001";
        String validEmail = "tester1@test.com";
        String validPassword1 = "passw0rd";
        String validPassword2 = "passw0rd";
        String validGivenName = "John";
        String validSurname = "Smith";
        NewUserRequest stubbedReq = new NewUserRequest(validUsername, validEmail, validPassword1, validPassword2,
                validGivenName, validSurname);

        // Act
        User createdUser = spySut.signup(stubbedReq);

        // Assert
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertNotNull(createdUser.getUsername());
        assertNotNull(createdUser.getPassword());
        assertNotEquals("", createdUser.getUsername());
        assertEquals("", createdUser.getRoleId());
        Mockito.verify(mockUserDao, Mockito.times(1)).save(createdUser);
    }

    @Test
    public void test_login_persistUserGivenValidUsernameAndPassword () {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String validUsername = "tester001";
        String validPassword = "passw0rd";
        User stubbedUser = new User(UUID.randomUUID().toString(), validUsername, "tester1@test.com",
                validPassword, "John", "Smith", true, "");
        NewLoginRequest stubbedReq = new NewLoginRequest(validUsername, validPassword);

        Mockito.when(mockUserDao.findByUsernameAndPassword(validUsername, validPassword)).thenReturn(stubbedUser);

        // Act
        Principal principal = spySut.login(stubbedReq);

        // Assert
        assertNotNull(principal);
        assertNotNull(principal.getUserId());
        assertNotNull(principal.getUsername());
        assertNotEquals("", principal.getUsername());
        Mockito.verify(mockUserDao, Mockito.times(1)).findByUsernameAndPassword(
                stubbedReq.getUsername(), stubbedReq.getPassword());;
    }
}