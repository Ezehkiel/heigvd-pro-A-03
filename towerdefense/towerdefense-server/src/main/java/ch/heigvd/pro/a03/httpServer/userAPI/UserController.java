package ch.heigvd.pro.a03.httpServer.userAPI;

import com.google.gson.Gson;

import java.util.logging.Logger;

import static ch.heigvd.pro.a03.httpServer.userAPI.JsonUtil.json;
import static spark.Spark.*;

public class UserController {

    final static Logger LOG = Logger.getLogger(UserController.class.getName());

    Gson gson = new Gson();


    public UserController(final UserService userService) {

        get("/users/scores", (req, res) -> userService.getAllScores(), json());

        post("/users/scores", (req, res) -> userService.setScore(req, res), json());

        get("/users/scores/:id", (req, res) -> userService.getUserScore(req, res), json());

        get("/users", (req, res) -> userService.getAllUsers(), json());

        post("/users/register", (req, res) -> userService.createUser(req), json());

        post("/users/login", (req, res) -> userService.loginUser(req), json());

        after((req, res) -> {

            res.type("application/json");
        });

    }

}
