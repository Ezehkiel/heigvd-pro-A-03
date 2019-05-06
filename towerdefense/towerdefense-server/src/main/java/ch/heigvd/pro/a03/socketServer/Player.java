package ch.heigvd.pro.a03.socketServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player {
    Socket socket;
    static int nextId=1;
    int id;

    InputStreamReader in;
    OutputStreamWriter out;


    public Player(Socket socket) {
        this.socket = socket;
        this.id = nextId++;
        try {
            in = new InputStreamReader(socket.getInputStream());
            out = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public InputStreamReader getIn() {
        return in;
    }

    public OutputStreamWriter getOut() {
        return out;
    }

    public int getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }
}
