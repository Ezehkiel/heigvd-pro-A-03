package ch.heigvd.pro.a03.server;
import ch.heigvd.pro.a03.commands.Executable;
import ch.heigvd.pro.a03.utils.Protocole;
import com.oracle.tools.packager.Log;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class GameClient {

    public static final Logger LOG = Logger.getLogger(GameClient.class.getSimpleName());

    private final String HOST;
    private final int PORT;

    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    private int playerNumber = -1;

    public GameClient() {
        HOST = "localhost";
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
                    Protocole.sendProtocol(out, 1, "START");
                    Protocole.receive(in);

                    Protocole.sendProtocol(out, 1, "2");
                    Protocole.receive(in);
                    Protocole.receive(in);

                    LOG.info("Connected to game server!");

                    command.execute();

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
     * Gets the players of the game.
     */
    public void getPlayers(Executable showPlayer, Executable showReadyButton) {
        new Thread(() -> {
            try {

                Protocole.sendProtocol(out, 2, "START");

                objectIn = new ObjectInputStream(socket.getInputStream());
                objectOut = new ObjectOutputStream(socket.getOutputStream());

                Protocole protocole = Protocole.receive(in);
                while (!protocole.getData().equals("END")) {
                    showPlayer.execute();
                    protocole = Protocole.receive(in);
                }

                Log.info("Game party if full.");

                Protocole.sendProtocol(out, 3, "START");
                Protocole.receive(in);

                showReadyButton.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Tells the server that the player is ready.
     */
    public void ready(Executable startGame) {
        new Thread(() -> {
            try {
                Protocole.sendProtocol(out, 3, "YES");
                Protocole.receive(in);

                LOG.info("Game is starting");
                startGame.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Tells the server that the player has left the game.
     */
    public void quit() {

        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
