package ch.heigvd.pro.a03.httpServer;

import ch.heigvd.pro.a03.httpServer.userAPI.UserController;
import ch.heigvd.pro.a03.httpServer.userAPI.UserService;
import com.google.gson.Gson;

import java.util.logging.Logger;

import static spark.Spark.port;



public class HttpServer implements Runnable {
    final static Logger LOG = Logger.getLogger(HttpServer.class.getName());

    int port;
    private Gson gson = new Gson();

    public HttpServer(int port) {
        port(port);
        this.port = port;


    }

    @Override
    public void run() {
        LOG.info("Starting the http server on port :" + this.port);

        new UserController(new UserService());


    }
}


