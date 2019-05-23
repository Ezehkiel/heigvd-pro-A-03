package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.httpServer.HttpServer;
import ch.heigvd.pro.a03.socketServer.SocketServer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static spark.Spark.secure;

/**
 * This class will create the two main threads for the socker and for HTTP
 * server. This is the entrance for the module towerdefense-server
 *
 * @author Didier Page, Rémi Poulard
 */
public class Server {

    public static boolean HAS_HTTP;
    private static String serverHTTP;
    private static String serverPort;
    private static boolean https;

    public static void main(String[] args) {


        HAS_HTTP = args.length == 1 && args[0].equals("--http");

        // Change Logger format
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        if (HAS_HTTP) {

            /* Get properties from the config file of the server */
            Properties defaults = new Properties();
            try {
                defaults.load(new FileReader("towerdefense-server/deploy/config.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            https = Boolean.parseBoolean(defaults.getProperty("https"));
            serverHTTP = defaults.getProperty("serverHTTP");
            serverPort = defaults.getProperty("serverPort");

            if(https){

                System.out.println("HTTPS is enabled");
                String password = defaults.getProperty("keystorePassword");
                String keyStoreLocation = "towerdefense-server/deploy/keystore.jks";
                secure(keyStoreLocation, password, null, null);
            }
            System.out.println("Launching HTTP server ...");


            // Run HTTP on other thread;
            new Thread(HttpServer.getInstance()).start();
        }

        System.out.println("Launching game server ...");
        new Thread(SocketServer.getInstance()).start();
    }

    public static String getServerHTTP() {
        return serverHTTP;
    }

    public static String getServerPort() {
        return serverPort;
    }

    public static boolean isHttps() {
        return https;
    }
}
