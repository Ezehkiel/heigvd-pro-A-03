package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.Player;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Object used to save all stream about the client and proivde usefull getters and setteers
 */
public class Client {

    Socket socket;
    private Player player;

    BufferedReader in;
    BufferedWriter out;
    ObjectInputStream ois = null;
    ObjectOutputStream ous = null;

    public Client(Socket socket) {

        this.player = null;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
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
