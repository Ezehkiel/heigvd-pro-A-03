package ch.heigvd.pro.a03.server;

import ch.heigvd.pro.a03.utils.Communication;
import ch.heigvd.pro.a03.utils.Protocole;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class GameClient {

    private static GameClient instance;

    private final String HOST;
    private final int PORT;

    private Socket socket;
    private OutputStreamWriter out;
    private InputStreamReader in;

    private int playerNumber = -1;

    private GameClient() {
        HOST = "ezehkiel.ch";
        PORT = 4567;
    }

    public static GameClient getInstance() {

        if (instance == null) {
            instance = new GameClient();
        }

        return instance;
    }

    /**
     * Connect to the server with a socket
     * @return true if connected to the server
     */
    public boolean connect() {

        try {
            Socket socket = new Socket(HOST, PORT);

            out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            in = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);

            //Communication.writeProtocol(out, Protocole.CLIENTWANTPLAYMULTI);

            return false;//Communication.readProtocol(in) == Protocole.ISCLIENTREADY;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void ready() {


    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
