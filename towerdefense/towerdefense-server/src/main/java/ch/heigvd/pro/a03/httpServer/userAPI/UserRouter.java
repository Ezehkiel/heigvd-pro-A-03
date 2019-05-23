package ch.heigvd.pro.a03.httpServer.userAPI;

import static ch.heigvd.pro.a03.httpServer.userAPI.JsonUtil.json;
import static spark.Spark.*;

/**
 * This class regroup all endpoint that we have. When a request arrive
 * the class look if there are an endpoint corresponding. If the endpoint
 * exist it will execute the method associated
 */
public class UserRouter {

    /**
     * Constructor
     * @param userController
     */
    public UserRouter(final UserController userController) {

        /* Get all scores */
        get("/users/scores", (req, res) -> userController.getAllScores(), json());
        /* Post new scores (used at the end aof a game */
        post("/users/scores", (req, res) -> userController.setScore(req), json());
        /* Get the scores for one user */
        get("/users/scores/:id", (req, res) -> userController.getUserScore(req), json());
        /* get all users */
        get("/users", (req, res) -> userController.getAllUsers(), json());
        /* Post to register a new user */
        post("/users/register", (req, res) -> userController.createUser(req), json());
        /* Post to login a user*/
        post("/users/login", (req, res) -> userController.loginUser(req), json());

        /* All our communication are formated with JSON */
        after((req, res) -> {

            res.type("application/json");
        });

    }

}
