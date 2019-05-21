package ch.heigvd.pro.a03.httpServer.userAPI;

import static ch.heigvd.pro.a03.httpServer.userAPI.JsonUtil.json;
import static spark.Spark.*;

/**
 * This class regroup all endpoint that we have. When a request arrive
 * the class look if there are an endpoint corresponding. If the endpoint
 * exist it will execute the method associated
 */
public class UserController {

    public UserController(final UserService userService) {

        get("/users/scores", (req, res) -> userService.getAllScores(), json());

        post("/users/scores", (req, res) -> userService.setScore(req), json());

        get("/users/scores/:id", (req, res) -> userService.getUserScore(req), json());

        get("/users", (req, res) -> userService.getAllUsers(), json());

        post("/users/register", (req, res) -> userService.createUser(req), json());

        post("/users/login", (req, res) -> userService.loginUser(req), json());

        /* All our communication are formated with JSON */
        after((req, res) -> {

            res.type("application/json");
        });

    }

}
