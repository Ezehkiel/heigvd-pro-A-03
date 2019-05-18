package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.httpServer.HttpServer;
import ch.heigvd.pro.a03.socketServer.SocketServer;

import static spark.Spark.secure;

/**
 * This class will create the two main threads for the socker and for HTTP
 * server. This is the entrance for the module towerdefense-server
 *
 * @author Didier Page, Rémi Poulard
 */
public class Server {

    public static boolean HAS_HTTP;

    public static void main(String[] args) {


        HAS_HTTP = args.length == 1 && args[0].equals("--http");

        // Change Logger format
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        if (HAS_HTTP) {
            System.out.println("Launching HTTP server ...");

            String keyStoreLocation = "towerdefense-server/deploy/keystore.jks";
            String keyStorePassword = "pro2019heig";
            secure(keyStoreLocation, keyStorePassword, null, null);


            // Run HTTP on other thread;
            new Thread(HttpServer.getInstance()).start();
        }

        System.out.println("Launching game server ...");
        new Thread(SocketServer.getInstance()).start();
    }
}
