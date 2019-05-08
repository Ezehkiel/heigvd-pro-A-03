package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.Player;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Player {
    Socket socket;


    BufferedReader in;
    BufferedWriter out;
    ObjectInputStream ois = null;
    ObjectOutputStream ous = null;

    public Client(Socket socket,String username) {
        super(username);
        this.socket = socket;
        try {
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            in = new BufferedReader( new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            ous = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public int getId() {
        return super.ID;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOus() {
        return ous;
    }

    public Socket getSocket() {
        return socket;
    }
}
