package ch.heigvd.pro.a03.socketServer;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player {
    Socket socket;
    static int nextId=1;
    int id;

    BufferedReader in;
    PrintWriter out;


    public Player(Socket socket) {
        this.socket = socket;
        this.id = nextId++;
        try {
            in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public int getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }
}
