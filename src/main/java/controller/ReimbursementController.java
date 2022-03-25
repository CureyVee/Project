package controller;

import io.javalin.http.Context;
import models.JsonResponse;
import models.Reimbursement;
import models.User;
import services.ReimbursementService;
import services.UserService;

import java.util.List;

public class ReimbursementController {
    private ReimbursementService reimbService;

    public ReimbursementController(ReimbursementService reimbService) {
        this.reimbService = reimbService;
    }

    public ReimbursementController() {
        this.reimbService = new ReimbursementService();
    }

    public void createReimbursement (Context context){
        Reimbursement reimbToCreate = context.bodyAsClass(Reimbursement.class);



        Reimbursement reimbFromDb = reimbService.createReimbursement(reimbToCreate);
        if (reimbFromDb == null){
            context.json(new JsonResponse(false, "An error ahas occurred, please try again", null));

        } else{
            context.json(new JsonResponse(true, "Reimbursement successfully created!", reimbFromDb));
        }

    }

    public void getAll (Context context){
        UserService userService = new UserService();

        //get user id in path param instead of request body
        Integer userId = Integer.parseInt(context.queryParam("userId"));

        // retrieve user given id
        User currentUser = userService.getUserGivenId(userId);

        //check if user is a manager
        if (currentUser.isManager()) {

            context.json(new JsonResponse(true, "All Reimbursements for All Users: ", reimbService.getAllForAllUsers(currentUser)));
        } else {

            context.json(new JsonResponse(true, "All Past Reimbursements for User: " + currentUser.getUsername(), reimbService.getAllGivenUser(currentUser.getId())));
        }



    } // Done

    public void changeStatus(Context context) {
        UserService userService = new UserService();
        Integer userId = Integer.parseInt(context.queryParam("userId"));
        Integer reimbId = Integer.parseInt(context.queryParam("reimbId"));
        Integer newStatusId = Integer.parseInt(context.queryParam("statusId"));

        User currentUser = userService.getUserGivenId(userId);

        if (currentUser.isManager()){
            reimbService.changeStatus(currentUser,reimbId,newStatusId);
            context.json(new JsonResponse(true,"Status updated to " + "_____" + "for reimbursement Id " + reimbId,null));
        }


    }

    public void filterByStatus(Context context) {
        UserService userService = new UserService();

        //get user id and status id in query param instead of request body
        Integer userId = Integer.parseInt(context.queryParam("userId"));
        Integer statusId = Integer.parseInt(context.queryParam("statusId"));

        // retrieve user given id
        User currentUser = userService.getUserGivenId(userId);

        if (currentUser.isManager()){
            List<Reimbursement> returnedList = reimbService.filterByStatusId(currentUser,statusId);
            context.json(new JsonResponse(true,"All Reimbursements Filtered by Status: ", returnedList));
        }

    }

    public void filterByType(Context context) {
        UserService userService = new UserService();

        //get user id and status id in query param instead of request body
        Integer userId = Integer.parseInt(context.queryParam("userId"));
        Integer typeId = Integer.parseInt(context.queryParam("typeId"));

        // retrieve user given id
        User currentUser = userService.getUserGivenId(userId);


        if (currentUser.isManager()){
            List<Reimbursement> returnedList = reimbService.filterByTypeId(currentUser,typeId);
            context.json(new JsonResponse(true,"All Reimbursements Filtered by Type: ", returnedList));
        }

    }
}

