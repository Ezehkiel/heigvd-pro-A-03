package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.httpServer.HttpServer;
import ch.heigvd.pro.a03.socketServer.SocketServer;
import ch.heigvd.pro.a03.utils.Protocole;

public class Server{



    public static void main(String[] args) {

        //String keyStoreLocation = "deploy/keystore.jks";
        //String keyStorePassword = "pro2019heig";
        //secure(keyStoreLocation, keyStorePassword, null, null);
        // Run HTTP on other thread;
        new Thread(new HttpServer(3945)).start();
        new Thread(new SocketServer(4567)).start();
    }
}
