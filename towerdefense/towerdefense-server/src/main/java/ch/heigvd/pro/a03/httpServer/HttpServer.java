package ch.heigvd.pro.a03.httpServer;

import ch.heigvd.pro.a03.httpServer.userAPI.UserController;
import ch.heigvd.pro.a03.httpServer.userAPI.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.logging.Logger;

import static spark.Spark.port;



public class HttpServer implements Runnable {
    final static Logger LOG = Logger.getLogger(HttpServer.class.getName());

    int port;

    public String getToken() {
        return token;
    }

    private String token;

    public HttpServer(int port) {
        port(port);
        this.port = port;
        Algorithm algorithm = Algorithm.HMAC256("P2z6cA9CGt5Oq");
        this.token = JWT.create().sign(algorithm);

    }

    @Override
    public void run() {
        LOG.info("Starting the http server on port :" + this.port);

        new UserController(new UserService());
        System.out.println(this.token);

    }
}


