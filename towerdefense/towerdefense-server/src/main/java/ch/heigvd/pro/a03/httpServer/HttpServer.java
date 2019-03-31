package ch.heigvd.pro.a03.httpServer;

import ch.heigvd.pro.a03.HelloWorld;
import ch.heigvd.pro.a03.socketServer.SocketServer;

import java.util.logging.Logger;
import static spark.Spark.*;

public class HttpServer implements Runnable {
    final static Logger LOG = Logger.getLogger(SocketServer.class.getName());
    int port;

    public HttpServer(int port) {
        port(port);
        this.port = port;
    }

    @Override
    public void run() {
        LOG.info("Starting the http server on port :"+this.port);
        get("/hello/:name", (request, response) -> HelloWorld.greet(request.params(":name")));
    }
}
