package ch.heigvd.pro.a03.httpServer.userAPI;

import ch.heigvd.pro.a03.httpServer.SqlRequest;
import ch.heigvd.pro.a03.users.User;
import com.google.gson.Gson;
import spark.*;

public class UserController {


    public static Route userCreation = (Request request, Response response) -> {
        Gson g = new Gson();
        SqlRequest sqlrequest = new SqlRequest();
        long id;
        User person = g.fromJson(request.body(), User.class);

        if(sqlrequest.createUser(person.getUsername(), person.getPassword())){
            response.status(200);
            response.body("User created with success");
            return "OK";
        }else{
            response.status(400);
            response.body("Failed to create user");
            return "not OK";
        }


    };

    public static Route userLogin = (Request request, Response response) -> {
        Gson g = new Gson();
        SqlRequest sqlrequest = new SqlRequest();

        User userLogin = g.fromJson(request.body(), User.class);
        User userDb = sqlrequest.checkLogin(userLogin.getUsername());

        if (userLogin.equals(userDb)){
            response.status(200);
            return "User is in DB";
        }else{
            response.status(400);
            return "User is not in DB";
        }


    };



}
