package ch.heigvd.pro.a03.httpServer.userAPI;

import ch.heigvd.pro.a03.users.User;
import com.google.gson.Gson;

import java.util.logging.Logger;

import static ch.heigvd.pro.a03.httpServer.userAPI.JsonUtil.json;
import static spark.Spark.*;

public class UserController {

    final static Logger LOG = Logger.getLogger(UserController.class.getName());


    public UserController(final UserService userService){

        Gson gson = new Gson();


        get("/users", (req, res) -> userService.getAllUsers(), json());

        get("/users/:username", (req, res) -> userService.getUser(req.params(":username")), json());

        post("/users/register", (req, res) -> userService.createUser(gson.fromJson(req.body(), User.class).getUsername(), gson.fromJson(req.body(), User.class).getPassword()), json());

        post("/users/login", (req, res) -> userService.canLogin(gson.fromJson(req.body(), User.class).getUsername(), gson.fromJson(req.body(), User.class).getPassword()), json());

        // put("/users", (req, res) -> userService.updateUser(gson.fromJson(req.body(), User.class).getUsername(),gson.fromJson(req.body(), User.class).getPassword()), json());



        after((req, res) -> {
            res.type("application/json");
        });

    }

}
