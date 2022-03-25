package controller;

import io.javalin.http.Context;
import models.JsonResponse;
import models.User;
import services.UserService;

public class SessionController {
    private UserService userService;

    public SessionController() {
        this.userService = new UserService();
    }

    //Logins in the User
    public void login (Context context){
        JsonResponse jsonResponse;

        User credentials = context.bodyAsClass(User.class);

        User userFromDB = userService.validateCredentials(credentials.getUsername(), credentials.getPassword());

        if(userFromDB == null){
            jsonResponse = new JsonResponse(false, "Invalid Username or Password", null);
        }else{
            context.sessionAttribute("logedInUser", userFromDB);
            jsonResponse = new JsonResponse(true, "Login Successful", userFromDB);
        }

        context.json(jsonResponse);
    }

    //Displays Reimbursements created by Employee
    public void ViewReimb(Context context){
        User loggedInUser = context.sessionAttribute("logedInUser");

        if(loggedInUser == null){
            context.json(new JsonResponse(false, "no reimbursements found", null));
        }else{
            context.json(new JsonResponse(true, "reimbursements found", loggedInUser));
        }

    }

    //Logout of the User
    public void logout(Context context){
        context.sessionAttribute("loggedInUser", null);

        context.json(new JsonResponse(true, "session invalidated", null));
    }

}
