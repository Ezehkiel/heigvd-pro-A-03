package ch.heigvd.pro.a03.httpServer;

import ch.heigvd.pro.a03.HelloWorld;
import ch.heigvd.pro.a03.httpServer.userAPI.AuthUser;
import ch.heigvd.pro.a03.httpServer.userAPI.CreateUser;
import ch.heigvd.pro.a03.socketServer.MultiThreadedServer;
import com.google.gson.Gson;

import java.util.logging.Logger;
import static spark.Spark.*;

public class HttpServer implements Runnable {
    final static Logger LOG = Logger.getLogger(MultiThreadedServer.class.getName());
    int port;
    private Gson gson = new Gson();

    public HttpServer(int port) {
        port(port);
        this.port = port;

    }

    @Override
    public void run() {
        LOG.info("Starting the http server on port :" + this.port);
        get("/hello/:name", (request, response) -> HelloWorld.greet(request.params(":name")));
        post("/user/login", (request, response) -> AuthUser.isValide(request.body()));
        post("/user/register", (request, response) -> CreateUser.create(request.body()));

    }
}
