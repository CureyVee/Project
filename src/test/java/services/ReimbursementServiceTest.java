package services;


import models.Reimbursement;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositories.ReimbursementDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementServiceTest {

    ReimbursementService reimbService;

    ReimbursementDAO reimbDAO = Mockito.mock(ReimbursementDAO.class);

    public ReimbursementServiceTest() {
        reimbService =  new ReimbursementService(reimbDAO);
    }

    @Test
    void getAllGivenUser() {
        //arrange
        Integer userId = 1;
        List<Reimbursement> expectedOutput = new ArrayList<>();
        expectedOutput.add(new Reimbursement(50, userId, 2));
        expectedOutput.add(new Reimbursement(100, userId, 3));
        Mockito.when(reimbDAO.getReimbursementsGivenUser(userId)).thenReturn(expectedOutput);

        //act
        List<Reimbursement> actualOutput = reimbService.getAllGivenUser(userId);


        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void createReimbursement() {
        //arrange
        Reimbursement reimbToCreate = new Reimbursement(250,1,2);

        //act
        reimbService.createReimbursement(reimbToCreate);

        //assert
        Mockito.verify(reimbDAO,Mockito.times(1)).createReimbursement(reimbToCreate);


    }

    @Test
    void getAllForAllUsersAccessDenied() {
        //arrange
        User userToPass = new User("username","password",1);
        List<Reimbursement> expectedOutput = null;

        //act
        List<Reimbursement> actualOutput = reimbService.getAllForAllUsers(userToPass);

        //assert
        assertNull(actualOutput);
    }

    @Test
    void getAllForAllUsersAccessAuthorized() {
        //arrange
        User userToPass = new User("username","password",2);
        List<Reimbursement> expectedOutput = new ArrayList<>();
        expectedOutput.add (new Reimbursement(50,1,2));
        expectedOutput.add (new Reimbursement(100,2,1));
        Mockito.when(reimbDAO.getReimbursementsForAllUsers()).thenReturn(expectedOutput);

        //act
        List<Reimbursement> actualOutput = reimbService.getAllForAllUsers(userToPass);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void AccessDenied() {
        // arrange
        User userToPass = new User("username","password",1);
        Integer reimbId = 2;
        Integer newStatusId = 3;


        // act
        Boolean actualOutput = reimbService.changeStatus(userToPass, reimbId, newStatusId);

        // assert
        assertFalse(actualOutput);
    }

    @Test
    void UserNotFound() {
        // arrange
        User userToPass = null;
        Integer reimbId = 2;
        Integer newStatusId = 3;


        // act
        Boolean actualOutput = reimbService.changeStatus(userToPass, reimbId, newStatusId);

        // assert
        assertFalse(actualOutput);
    }

    @Test
    void AccessAuthorized() {
        // arrange
        User userToPass = new User("username","password",2);
        Integer reimbId = 2;
        Integer newStatusId = 3;


        // act
        Boolean actualOutput = reimbService.changeStatus(userToPass, reimbId, newStatusId);

        // assert
        assertTrue(actualOutput);
    }

    @Test
    void filterByIdApproved() {
        //arrange
        User userToPass = new User("username","password",2);
        Integer statusId = 2;
        List<Reimbursement> expectedOutput = new ArrayList<>();
        expectedOutput.add (new Reimbursement(50,1,2));
        expectedOutput.add (new Reimbursement(100,2,1));
        Mockito.when(reimbDAO.getReimbursementsGivenStatusId(statusId)).thenReturn(expectedOutput);

        //act
        List<Reimbursement> actualOutput = reimbService.filterByStatusId(userToPass, statusId);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void filterByIdDenied() {
        //arrange
        User userToPass = new User("username","password",1);
        Integer statusId = 2;
        List<Reimbursement> expectedOutput = null;

        //act
        List<Reimbursement> actualOutput = reimbService.filterByStatusId(userToPass, statusId);

        //assert
        assertNull(actualOutput);
    }

    @Test
    void filterByTypeIdAccessAuthorized() {
        //arrange
        User userToPass = new User("username","password",2);
        Integer typeId = 2;
        List<Reimbursement> expectedOutput = new ArrayList<>();
        expectedOutput.add (new Reimbursement(50,1,2));
        expectedOutput.add (new Reimbursement(100,2,1));
        Mockito.when(reimbDAO.getReimbursementsGivenTypeId(typeId)).thenReturn(expectedOutput);

        //act
        List<Reimbursement> actualOutput = reimbService.filterByTypeId(userToPass, typeId);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void filterByTypeIdAccessDenied() {
        //arrange
        User userToPass = new User("username","password",1);
        Integer typeId = 2;
        List<Reimbursement> expectedOutput = null;

        //act
        List<Reimbursement> actualOutput = reimbService.filterByStatusId(userToPass, typeId);

        //assert
        assertNull(actualOutput);
    }
}