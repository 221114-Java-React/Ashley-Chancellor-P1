package com.revature.ers.services;

import com.revature.ers.daos.UserDAO;
import com.revature.ers.dtos.requests.NewLoginRequest;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService sut;
    private final UserDAO mockUserDAO = Mockito.mock(UserDAO.class);

    @Before
    public void init() {
        sut = new UserService(mockUserDAO);
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

        Mockito.when(mockUserDAO.findAllUsernames()).thenReturn(stubbedUsernames);

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

        Mockito.when(mockUserDAO.findAllUsernames()).thenReturn(stubbedUsernames);

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

        Mockito.when(mockUserDAO.findAllEmails()).thenReturn(stubbedEmails);

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

        Mockito.when(mockUserDAO.findAllEmails()).thenReturn(stubbedEmails);

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
    public void test_signup_persistUserGivenValidParameters() {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String validUsername = "tester001";
        String validEmail = "tester1@test.com";
        String validPassword1 = "passw0rd";
        String validPassword2 = "passw0rd";
        String validGivenName = "John";
        String validSurname = "Smith";
        NewUserRequest stubbedReq = new NewUserRequest
                (validUsername, validEmail, validPassword1, validPassword2, validGivenName, validSurname);

        // Act
        User createdUser = spySut.signup(stubbedReq);

        // Assert
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(validUsername, createdUser.getUsername());
        assertEquals(validEmail, createdUser.getEmail());
        assertEquals(validPassword1, createdUser.getPassword());
        assertEquals(validGivenName, createdUser.getGivenName());
        assertEquals(validSurname, createdUser.getSurname());
        assertFalse(createdUser.isActive());
        assertEquals("05836bdd-83c4-4ecb-a255-c7f1f7e0bd40", createdUser.getRoleId());
        Mockito.verify(mockUserDAO, Mockito.times(1)).save(createdUser);
    }

    @Test
    public void test_login_persistUserGivenValidUsernameAndPassword() {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String validUsername = "tester001";
        String validPassword = "passw0rd";
        User stubbedUser = new User
                (UUID.randomUUID().toString(), validUsername, "tester1@test.com", validPassword,
                        "John", "Smith", true, "05836bdd-83c4-4ecb-a255-c7f1f7e0bd40");
        NewLoginRequest stubbedReq = new NewLoginRequest(validUsername, validPassword);

        Mockito.when(mockUserDAO.findByUsernameAndPassword(validUsername, validPassword)).thenReturn(stubbedUser);

        // Act
        Principal principal = spySut.login(stubbedReq);

        // Assert
        assertNotNull(principal);
        assertNotNull(principal.getUserId());
        assertEquals(validUsername, principal.getUsername());
        assertNotNull(principal.getEmail());
        assertNotEquals("", principal.getEmail());
        assertNotNull(principal.getGivenName());
        assertNotEquals("", principal.getGivenName());
        assertNotNull(principal.getSurname());
        assertNotEquals("", principal.getSurname());
        assertTrue(principal.isActive());
        assertNotNull(principal.getRoleId());
        assertNotEquals("", principal.getRoleId());
        Mockito.verify(mockUserDAO, Mockito.times(1)).findByUsernameAndPassword(
                stubbedReq.getUsername(), stubbedReq.getPassword());;
    }

    @Test
    public void test_getAllUsers_givenUsers() {
        // Arrange
        User stubbedUser1 = new User();
        User stubbedUser2 = new User();
        User stubbedUser3 = new User();
        List <User> stubbedUsers = Arrays.asList(stubbedUser1, stubbedUser2, stubbedUser3);
        Mockito.when(mockUserDAO.findAll()).thenReturn(stubbedUsers);

        // Act
        List<User> condition = sut.getAllUsers();

        // Assert
        assertFalse(condition.isEmpty());
    }

    @Test
    public void test_getAllUsers_givenNoUsers() {
        // Arrange
        List <User> stubbedUsers = new ArrayList<>();
        Mockito.when(mockUserDAO.findAll()).thenReturn(stubbedUsers);

        // Act
        List<User> condition = sut.getAllUsers();

        // Assert
        assertTrue(condition.isEmpty());
    }

    @Test
    public void test_getAllUsersByUsername_givenValidUsername() {
        // Arrange
        String validUsername = "test";
        User stubbedUser1 = new User();
        User stubbedUser2 = new User();
        User stubbedUser3 = new User();
        List <User> stubbedUsers = Arrays.asList(stubbedUser1, stubbedUser2, stubbedUser3);
        Mockito.when(mockUserDAO.findAllByUsername(validUsername)).thenReturn(stubbedUsers);

        // Act
        List<User> condition = sut.getAllUsersByUsername(validUsername);

        // Assert
        assertFalse(condition.isEmpty());
    }

    @Test
    public void test_getAllUsersByUsername_givenInvalidUsername() {
        // Arrange
        String invalidUsername = "ScruffyC";
        List <User> stubbedUsers = new ArrayList<>();
        Mockito.when(mockUserDAO.findAllByUsername(invalidUsername)).thenReturn(stubbedUsers);

        // Act
        List<User> condition = sut.getAllUsersByUsername(invalidUsername);

        // Assert
        assertTrue(condition.isEmpty());
    }

    @Test
    public void test_setPassword_givenValidId() {
        // Arrange
        String validId = UUID.randomUUID().toString();
        String password = "passw0rd";
        User stubbedUser = new User();

        Mockito.when(mockUserDAO.findById(validId)).thenReturn(stubbedUser);

        // Act
        User condition = sut.setPassword(validId, password);

        // Assert
        assertEquals(password, condition.getPassword());
    }

    @Test
    public void test_setPassword_givenInvalidId() {
        // Arrange
        String invalidId = UUID.randomUUID().toString();
        String password = "passw0rd";
        Mockito.when(mockUserDAO.findById(invalidId)).thenReturn(null);

        // Act
        User condition = sut.setPassword(invalidId, password);

        // Assert
        assertNull(condition);
    }

    @Test
    public void test_setActive_givenValidId() {
        // Arrange
        String validId = UUID.randomUUID().toString();
        User stubbedUser = new User();
        Mockito.when(mockUserDAO.findById(validId)).thenReturn(stubbedUser);

        // Act
        User condition = sut.setActive(validId);

        // Assert
        assertTrue(condition.isActive());
    }

    @Test
    public void test_setActive_givenInvalidId() {
        // Arrange
        String invalidId = UUID.randomUUID().toString();
        Mockito.when(mockUserDAO.findById(invalidId)).thenReturn(null);

        // Act
        User condition = sut.setActive(invalidId);

        // Assert
        assertNull(condition);
    }

    @Test
    public void test_setRole_givenValidId() {
        // Arrange
        String validId = UUID.randomUUID().toString();
        String roleId = "53069ab4-c085-47d5-9d0d-aafb6c3b475a";
        User stubbedUser = new User();
        Mockito.when(mockUserDAO.findById(validId)).thenReturn(stubbedUser);

        // Act
        User condition = sut.setRoleId(validId, roleId);

        // Assert
        assertEquals(roleId, condition.getRoleId());
    }

    @Test
    public void test_setRole_givenInvalidId() {
        // Arrange
        String invalidId = UUID.randomUUID().toString();
        String roleId = "53069ab4-c085-47d5-9d0d-aafb6c3b475a";
        Mockito.when(mockUserDAO.findById(invalidId)).thenReturn(null);

        // Act
        User condition = sut.setRoleId(invalidId, roleId);

        // Assert
        assertNull(condition);
    }
}