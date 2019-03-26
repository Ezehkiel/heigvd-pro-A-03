package ch.heigvd.pro.a03;

import com.google.gson.Gson;

import static spark.Spark.*;

public class Server {

    public Server() {
        Gson gson = new Gson();

        get("/hello/:name", (request, response) -> HelloWorld.greet(request.params(":name")), gson::toJson);
    }

    public static void main(String[] args) {



        new Server();
    }
}
