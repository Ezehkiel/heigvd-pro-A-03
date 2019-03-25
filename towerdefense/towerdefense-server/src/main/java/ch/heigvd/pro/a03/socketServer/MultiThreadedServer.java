package ch.heigvd.pro.a03.socketServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;


// RES Exemple
public class MultiThreadedServer implements Runnable{

    final static Logger LOG = Logger.getLogger(MultiThreadedServer.class.getName());
    static ArrayList<Socket> allClientConnected;

    int port;

    public MultiThreadedServer(int port) {
        this.port = port;

    }

    public void run() {
        LOG.info("Starting the socket server on port : " +port);
        //Start server
    }

}
