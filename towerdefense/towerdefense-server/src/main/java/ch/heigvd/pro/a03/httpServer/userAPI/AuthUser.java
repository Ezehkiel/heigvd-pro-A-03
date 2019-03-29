package ch.heigvd.pro.a03.httpServer.userAPI;

import ch.heigvd.pro.a03.users.User;
import com.google.gson.Gson;

public class AuthUser {

    public static boolean isValide(String body) {
        Gson g = new Gson();
        String usename;
        String password;

        User person = g.fromJson(body, User.class);

        System.out.println(person);

        return false;
    }
}