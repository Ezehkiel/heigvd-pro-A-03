package ch.heigvd.pro.a03.httpServer.userAPI;

import ch.heigvd.pro.a03.httpServer.SqlRequest;
import ch.heigvd.pro.a03.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import spark.*;

import java.util.logging.Logger;

public class UserController {

    final static Logger LOG = Logger.getLogger(UserController.class.getName());

    public static Route userCreation = (Request request, Response response) -> {
        LOG.info("New request to 'userCreation'");
        Gson g = new Gson();
        SqlRequest sqlrequest = new SqlRequest();
        long id;
        User person = g.fromJson(request.body(), User.class);

        if(sqlrequest.createUser(person.getUsername(), person.getPassword())){
            LOG.info("Creation of a new user: " + person.getUsername());
            response.status(200);
            response.body("User created with success");
            return "OK";
        }else{
            LOG.info("Failed to create user" + person.getUsername());
            response.status(400);
            response.body("Failed to create user");
            return "not OK";
        }


    };

    public static Route userLogin = (Request request, Response response) -> {
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



}
