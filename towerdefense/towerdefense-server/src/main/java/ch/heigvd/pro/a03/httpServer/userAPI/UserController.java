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

        post("/users/register", (req, res) -> userService.createUser(gson.fromJson(req.body(), User.class).getUsername(), gson.fromJson(req.body(), User.class).getPassword()), json());

        post("/users/login", (req, res) -> userService.canLogin(gson.fromJson(req.body(), User.class).getUsername(), gson.fromJson(req.body(), User.class).getPassword()), json());

        // put("/users", (req, res) -> userService.updateUser(gson.fromJson(req.body(), User.class).getUsername(),gson.fromJson(req.body(), User.class).getPassword()), json());



        after((req, res) -> {
            res.type("application/json");
        });

    }



/*
    public static Route userCreation = (Request request, Response response) -> {
        LOG.info("New request to 'userCreation'");
        Gson g = new Gson();
        SqlRequest sqlrequest = new SqlRequest();

        User person = g.fromJson(request.body(), User.class);

        System.out.println(person);

        User userindb = sqlrequest.createUser(person.getUsername(), person.getPassword());

        System.out.println(userindb);

        System.out.println(userindb.equals(person));

        if(userindb != null){
            LOG.info("Creation of a new user: " + person.getUsername());

            response.status(200);

        }else {
            LOG.info("Failed to create user" + person.getUsername());
            response.status(400);
            response.body("Failed to create user");
        }

        return om.writeValueAsString(response);

    };

    /*public static Route userLogin = (Request request, Response response) -> {
        LOG.info("New request to 'userLogin'");
        Gson g = new Gson();
        SqlRequest sqlrequest = new SqlRequest();

        User userLogin = g.fromJson(request.body(), User.class);
        User userDb = sqlrequest.checkLogin(userLogin.getUsername());

        if (userLogin.equals(userDb)){
            LOG.info("User can login");

            response.status(200);
            try {
                Algorithm algorithm = Algorithm.HMAC256("PRO2019");
                String token = JWT.create()
                        .withClaim("hasError", false)
                        .withClaim("message", "User exist in DB")
                        .withClaim("data", "username : "+userLogin.getUsername())
                        .sign(algorithm);
                System.out.println(token);
                return token;
            } catch (JWTCreationException exception){
                //Invalid Signing configuration / Couldn't convert Claims.
            }

        }else{
            LOG.info("Error with login");
            response.status(400);
            try {
                Algorithm algorithm = Algorithm.HMAC256("PRO2019");
                String token = JWT.create()
                        .withClaim("hasError", true)
                        .withClaim("message", "User does not exist in DB")
                        .withClaim("data", "username : "+userLogin.getUsername())
                        .sign(algorithm);
                System.out.println(token);
                return token;
            } catch (JWTCreationException exception){
                //Invalid Signing configuration / Couldn't convert Claims.
            }
        }

        return null;
    };
*/


}
