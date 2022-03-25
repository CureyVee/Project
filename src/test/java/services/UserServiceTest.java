package services;

import static org.junit.jupiter.api.Assertions.*;


import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositories.UserDAO;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;

    private UserDAO userDAO = Mockito.mock(UserDAO.class);

    public UserServiceTest() {
        this.userService = new UserService(userDAO);
    }

    @Test
    void validateCredentialsInvalidUsername() {
        //arrange
        String expectedUsername = "invalidUsername";
        String expectedPassword = "password";
        User expectedOutput = null;
        Mockito.when(userDAO.getUserGivenUsername(expectedUsername)).thenReturn(expectedOutput);

        //act
        User actualOutput = userService.validateCredentials(expectedUsername,expectedPassword);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);

    }

    @Test
    void validateCredentialsInvalidPassword() {
        //arrange
        String expectedUsername = "correctUsername";
        String expectedPassword = "password";
        User expectedOutput = null;
        User userRetrieved = new User("correctUsername", "pass1234");
        Mockito.when(userDAO.getUserGivenUsername(expectedUsername)).thenReturn(userRetrieved);

        //act
        User actualOutput = userService.validateCredentials(expectedUsername,expectedPassword);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void validateCredentialsValidCredentials() {
        //arrange
        String expectedUsername = "correctUsername";
        String expectedPassword = "correctPassword";
        User expectedOutput = new User(expectedUsername, expectedPassword);
        Mockito.when(userDAO.getUserGivenUsername(expectedUsername)).thenReturn(expectedOutput);

        //act
        User actualOutput = userService.validateCredentials(expectedUsername, expectedPassword);

        //assert
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void createUserUsernameUnavailable() {
        // arrange
        User userToCreate = new User("takenUsername", "password","firstName","lastName","email@email.com",1);
        User expectedOutput = new User("takenUsername", "password1","Mr.","Name Jr.","differentemail@email.com",2);
        Mockito.when(userDAO.getUserGivenUsername(userToCreate.getUsername())).thenReturn(expectedOutput);

        //act
        Boolean actualOutput = userService.createUser(userToCreate);

        //assert
        assertFalse(actualOutput);
    }

    @Test
    void createUserEmailUnavailable() {
        // arrange
        User userToCreate = new User("Username", "password","firstName","lastName","sameEmail@email.com",1);
        User expectedOutput = new User("differentUsername", "password1","Mr.","Name Jr.","sameEmail@email.com",2);
        Mockito.when(userDAO.getUserGivenEmail(userToCreate.getEmail())).thenReturn(expectedOutput);

        //act
        Boolean actualOutput = userService.createUser(userToCreate);

        //assert
        assertFalse(actualOutput);
    }

    @Test
    void createUserValidCredentials() {
        // arrange
        User userToCreate = new User("Username", "password","firstName","lastName","someEmail@email.com",1);
        User expectedOutput = new User("differentUsername", "password1","Mr.","Name Jr.","aDifferentEmail@email.com",2);
        Mockito.when(userDAO.getUserGivenEmail(userToCreate.getEmail())).thenReturn(expectedOutput);

        //act
        Boolean actualOutput = userService.createUser(userToCreate);

        //assert
        Mockito.verify(userDAO, Mockito.times(0)).createUser(userToCreate);
        assertTrue(actualOutput);
    }


}