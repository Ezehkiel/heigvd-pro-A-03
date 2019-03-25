package ch.heigvd.pro.a03;


import ch.heigvd.pro.a03.httpServer.HttpServer;
import ch.heigvd.pro.a03.socketServer.MultiThreadedServer;

public class Server {

    public static void main(String[] args) {



        new Server();


        // Run HTTP on other thread;

        new Thread(new HttpServer(Protocole.HTTPSERVERPORT)).start();
        new Thread(new MultiThreadedServer(Protocole.SOCKETSERVERPORT)).start();
    }
}