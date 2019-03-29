package ch.heigvd.pro.a03;

import com.google.gson.Gson;

import static spark.Spark.*;

import ch.heigvd.pro.a03.httpServer.HttpServer;
import ch.heigvd.pro.a03.socketServer.MultiThreadedServer;

import java.sql.Connection;

public class Server{


    public static void main(String[] args) {

        ConnectionDB conDB = new ConnectionDB();
        conDB.connecterBd();
        // Run HTTP on other thread;
        new Thread(new HttpServer(Protocole.HTTPSERVERPORT)).start();
        new Thread(new MultiThreadedServer(Protocole.SOCKETSERVERPORT)).start();
    }
}