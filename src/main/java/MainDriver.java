import controller.ReimbursementController;
import controller.SessionController;
import controller.UserController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import models.Reimbursement;
import models.User;
import repositories.ReimbursementDAO;
import repositories.ReimbursementDAOImpl;
import repositories.UserDAO;
import repositories.UserDAOImpl;
import services.ReimbursementService;
import services.UserService;

public class MainDriver {

    public static void main(String[] args) {
        //initializing javalin server
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/", Location.CLASSPATH);
        }).start(7777);

        // adding references to my controllers to be used later
        UserController userController = new UserController();
        ReimbursementController reimbController = new ReimbursementController();
        SessionController sessionController = new SessionController();

        // ------------- ALL EMPLOYEES
        // Session Controller Endpoints
        app.post("/login", sessionController::login);
        app.get("/session", sessionController::ViewReimb);
        app.delete("session",sessionController::logout);

        // User Controller Endpoints
        app.post("/register", userController::createUser); //throws error if username or email taken already

        // Reimbursement Controller Endpoints
        app.get("/reimb", reimbController::getAll);
        app.post("/reimb/new", reimbController::createReimbursement);

        // ----------- FINANCE MANAGERS ONLY

        app.patch("/reimb/changestatus/", reimbController::changeStatus);
        app.get("/reimb/filter/status",reimbController::filterByStatus);
        app.get("/reimb/filter/type",reimbController::filterByType);

    }
}