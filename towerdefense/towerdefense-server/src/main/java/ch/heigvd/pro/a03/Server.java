package ch.heigvd.pro.a03;

import com.google.gson.Gson;

import static spark.Spark.*;

public class Server {

    public Server() {

        get("/hello/:name", (request, response) -> HelloWorld.greet(request.params(":name")));
    }

    public static void main(String[] args) {



        new Server();
    }
}
