package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.httpServer.HttpServer;
import ch.heigvd.pro.a03.socketServer.SocketServer;

/**
 * This class will create the two main threads for the socker and for HTTP
 * server. This is the entrance for the module towerdefense-server
 *
 * @author Didier Page, Rémi Poulard
 */
public class Server {
    public static void main(String[] args) {

        // Change Logger format
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        //String keyStoreLocation = "towerdefense-server/deploy/keystore.jks";
        //String keyStorePassword = "pro2019heig";
        //secure(keyStoreLocation, keyStorePassword, null, null);
        // Run HTTP on other thread;
        new Thread(new HttpServer(3945)).start();
        new Thread(new SocketServer(4567)).start();
    }
}
