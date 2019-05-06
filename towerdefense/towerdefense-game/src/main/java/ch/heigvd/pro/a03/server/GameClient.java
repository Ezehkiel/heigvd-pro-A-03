package ch.heigvd.pro.a03.server;
import ch.heigvd.pro.a03.commands.Executable;
import ch.heigvd.pro.a03.utils.Protocole;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class GameClient {

    private final String HOST;
    private final int PORT;

    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;

    private int playerNumber = -1;

    public GameClient() {
        HOST = "ezehkiel.ch";
        PORT = 4567;
    }

    /**
     * Connect to the server with a socket
     * @return true if connected to the server
     */
    public boolean connect(Executable command) {

        try {
            socket = new Socket(HOST, PORT);

            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            new Thread(() -> {
                try {

                    Protocole.sendProtocol(out, 100, "START");
                    Protocole protocol = Protocole.receive(in);
                    if (protocol.getId() == 100) {
                        command.execute();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Tells the server that the player is ready
     */
    public void ready() {

    }

    /**
     * Tells the server that the player has left the game.
     */
    public void quit() {

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
