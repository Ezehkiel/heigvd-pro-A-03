package ch.heigvd.pro.a03.httpServer.userAPI;

import java.util.logging.Logger;

import static ch.heigvd.pro.a03.httpServer.userAPI.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;

public class UserController {

    final static Logger LOG = Logger.getLogger(UserController.class.getName());


    public UserController(final UserService userService){



        try {
            get("/users", (req, res) -> userService.getAllUsers(req, res), json());

            get("/users/:username", (req, res) -> userService.getUser(req, res), json());

            post("/users/register", (req, res) -> userService.createUser(req, res), json());

            post("/users/login", (req, res) -> userService.canLogin(req, res), json());

            // put("/users", (req, res) -> userService.updateUser(gson.fromJson(req.body(), User.class).getUsername(),gson.fromJson(req.body(), User.class).getPassword()), json());


            after((req, res) -> {

                res.type("application/json");
            });
        }catch (Exception ex){

        }

    }

}
