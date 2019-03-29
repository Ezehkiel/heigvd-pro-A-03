package ch.heigvd.pro.a03.httpServer.userAPI;

import ch.heigvd.pro.a03.users.User;
import com.google.gson.Gson;

public class CreateUser {


    public static Object create(String body) {
        Gson g = new Gson();
        String usename;
        String password;

        User person = g.fromJson(body, User.class);

        return null;
    }
}
