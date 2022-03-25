package controller;

import io.javalin.http.Context;
import models.JsonResponse;
import models.User;
import services.UserService;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserController() {
        this.userService = new UserService();
    }

    public void createUser (Context context){
        JsonResponse jsonResponse;

        User userToCreate = context.bodyAsClass(User.class);

        jsonResponse = (userService.createUser(userToCreate) ? new JsonResponse(true, "User has been created", userToCreate)
                : new JsonResponse(false, "Username or Email already taken", null));

        context.json(jsonResponse);
    }
}