package ch.heigvd.pro.a03.httpServer;

import ch.heigvd.pro.a03.httpServer.userAPI.UserRouter;
import ch.heigvd.pro.a03.httpServer.userAPI.UserController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.logging.Logger;

import static spark.Spark.port;


/**
 * This class creates an instance of HTTPserver. Its will start Spark and who
 * will listen on the different endpoint that we have created.
 *
 * @author Rémi Poulard, Didier Page
 */
public class HttpServer implements Runnable {

    final static Logger LOG = Logger.getLogger(HttpServer.class.getName());

    int port;
    private String token;
    private static HttpServer instance;


    public static HttpServer getInstance() {
        if(instance == null){
            instance = new HttpServer(3945);
        }
        return instance;
    }

    /**
     * Constructor
     *
     * @param port the port to listen on
     */
    private HttpServer(int port) {
        port(port);
        this.port = port;

        /* We create the token of the server. It will be used when the server
         * will add score via the API */
        this.token = JWT.create().sign(Algorithm.HMAC256("P2z6cA9CGt5Oq"));

    }

    /**
     * When the server start, the controller for endpoint is created
     */
    @Override
    public void run() {
        LOG.info("Starting the http server on port :" + this.port);

        new UserRouter(new UserController());
    }

    /**
     * get token of the server
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

}


