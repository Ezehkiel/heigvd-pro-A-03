package ch.heigvd.pro.a03.server;
import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.commands.Executable;
import ch.heigvd.pro.a03.event.Event;
import ch.heigvd.pro.a03.utils.Protocole;
import org.lwjgl.Sys;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
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

    public final int PLAYERS_COUNT;
    private Player player = null;

    public GameClient(int playersCount) {
        HOST = "localhost";
        PORT = 4567;
        PLAYERS_COUNT = playersCount;
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

                    Protocole.sendProtocol(out, 1, GameLauncher.getInstance().getConnectedPlayer().getUsername());
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

                Player player = Player.getPlayer(objectIn);
                while (player != null) {

                    showPlayer.execute(player);

                    player = Player.getPlayer(objectIn);
                }

                this.player = Player.getPlayer(objectIn);

                System.out.println("I am player " + this.player.ID);

                Protocole.receive(in);

                LOG.info("Game party full.");

                Protocole.sendProtocol(out, 3, "START");
                Protocole.receive(in);

                showReadyButton.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Tells the server that the player is ready. Blocks everything until every players are ready.
     */
    public void ready(Executable startGame) {
        try {
            Protocole.sendProtocol(out, 3, "YES");
            Protocole.receive(in);

            LOG.info("Game is starting");
            startGame.execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void firstTurn() {

        LOG.info("First Round starting.");

        new Thread(() -> {
            try {
                Protocole.sendProtocol(out, 4, "START");
                Protocole protocole = Protocole.receive(in);
                while (!protocole.getData().equals("END")) {
                    if (Integer.parseInt(protocole.getData()) == player.ID) {
                        LOG.info("My turn.");
                        Event.sendEvents(new LinkedList<>(), objectOut);
                        Protocole.receive(in);
                        LOG.info("I end my turn.");
                    } else {
                        LOG.info("Player " + protocole.getData() + "'s turn.");
                    }

                    protocole = Protocole.receive(in);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            round(args -> System.out.println("Received Maps."), args -> System.out.println("Received Map"));

        }).start();
    }

    public void round(Executable showMaps, Executable showMap) {
        LOG.info("Round starting.");

        new Thread(() -> {

            try {

                Protocole.sendProtocol(out, 5, "START");

                Map[] maps = (Map[]) receiveObject();
                System.out.println(maps);
                showMaps.execute();

                Protocole protocole = Protocole.receive(in);
                while (!protocole.getData().equals("END")) {

                    //Protocole.sendProtocol(out, 5, "OK");

                    if (Integer.parseInt(protocole.getData()) == player.ID) {

                        LOG.info("My Turn");
                        player = (Player) receiveObject();

                        Event.sendEvents(new LinkedList<>(), objectOut);
                        LOG.info("I end my turn.");

                    } else {
                        LOG.info("Player " + protocole.getData() + "'s turn.");
                    }

                    Map map = (Map) receiveObject();
                    System.out.println(map);
                    showMap.execute();

                    protocole = Protocole.receive(in);
                }

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

    private Object receiveObject() {
        try {
            return objectIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
