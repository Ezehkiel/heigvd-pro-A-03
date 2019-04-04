package ch.heigvd.pro.a03.httpServer;

import ch.heigvd.pro.a03.HelloWorld;
import ch.heigvd.pro.a03.httpServer.userAPI.UserController;
import ch.heigvd.pro.a03.httpServer.userAPI.UserService;
import com.google.gson.Gson;

import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.port;



public class HttpServer implements Runnable {
    final static Logger LOG = Logger.getLogger(HttpServer.class.getName());

    int port;
    private Gson gson = new Gson();

    public HttpServer(int port) {
        port(port);
        this.port = port;
        new UserController(new UserService());

    }

    @Override
    public void run() {
        LOG.info("Starting the http server on port :" + this.port);

        get("/hello/:name", (request, response) -> HelloWorld.greet(request.params(":name")));

        //post("/user/login",  userLogin,json());
        //post("/user/register", userCreation, json());
       // get("/users/all", userGetAll);


    }
}


