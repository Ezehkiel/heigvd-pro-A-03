package ch.heigvd.pro.a03.server;
import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.commands.Executable;
import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.utils.Config;
import ch.heigvd.pro.a03.utils.Protocole;
import ch.heigvd.pro.a03.utils.RandomPlayer;
import ch.heigvd.pro.a03.utils.Waiter;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Protocole.sendObject;

/**
 * Manages the communication protocol with the server
 */
public class GameClient {

    public static final Logger LOG = Logger.getLogger(GameClient.class.getSimpleName());

    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    public final int PLAYERS_COUNT;
    public final boolean ONLINE;
    private Player player = null;
    private TowerDefense game = null;

    /**
     * Creates a new client
     * @param playersCount players count
     * @param online true if connecting to online server
     */
    public GameClient(int playersCount, boolean online) {
        PLAYERS_COUNT = playersCount;
        ONLINE = online;
    }

    /**
     * Connect to the server with a socket
     * @return true if connected to the server
     */
    public boolean connect(Executable command) {

        LOG.info("Connecting to " + (ONLINE ? "online" : "offline") + " server.");

        try {
            socket = ONLINE ? new Socket(Config.getServerIp(), Config.getServerPort())
                    : new Socket(Config.getOfflineIp(), Config.getOfflinePort());

            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            new Thread(() -> {

                try {
                    Protocole.sendProtocol(out, 1, "START");
                    Protocole.receive(in);

                    Protocole.sendProtocol(out, 1,
                            ONLINE ? GameLauncher.getInstance().getConnectedPlayer().getUsername()
                                    : RandomPlayer.USER.getUsername()
                    );
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
            //TODO: close matchmaking scene
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

                String response = Protocole.receiveJson(in);
                while (response != null && !response.equals("200-OK")) {
                    showPlayer.execute(Player.fromJson(response));
                    response = Protocole.receiveJson(in);
                }

                this.player = Player.fromJson(Protocole.receiveJson(in));

                System.out.println("I am player " + this.player.ID);

                Protocole.receive(in);

                LOG.info("Game party full.");

                Protocole.sendProtocol(out, 3, "START");
                Protocole.receive(in);

                showReadyButton.execute();

            } catch (IOException e) {
                //TODO: close matchmaking scene
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
            //TODO: close matchmaking scene
        }
    }

    /**
     * Operates the first round
     * @param playerTurnStart a player starts his turn
     * @param playerTurnEnd a player ends hist turn
     * @param roundEnd the round ended
     * @param showMap show the maps
     * @param waitForEvents wait for player events
     */
    public void firstRound(Executable playerTurnStart, Executable playerTurnEnd,
                           Executable roundEnd, Executable showMap,
                           Waiter<PlayerEvent> waitForEvents) {

        LOG.info("First Round starting.");

        new Thread(() -> {
            try {
                Protocole.sendProtocol(out, 4, "START");

                receiveMaps(showMap);

                Protocole protocole = Protocole.receive(in);
                while (!protocole.getData().equals("END")) {

                    int id = Integer.parseInt(protocole.getData());

                    LOG.info("Player " + id + "'s turn start.");
                    playerTurnStart.execute(id);

                    if (id == player.ID) {

                        waitForEvents.waitData();

                        sendObject(objectOut,waitForEvents.receive());
                       // PlayerEvent.sendPlayerEvent(waitForEvents.receive(), objectOut);

                        Protocole.receive(in);
                    }

                    protocole = Protocole.receive(in);

                    LOG.info("Player " + id + "'s turn end.");
                    playerTurnEnd.execute(id);
                }
            } catch (IOException e) {
                quitGame();
            }

            roundEnd.execute();

        }).start();
    }

    /**
     * Operates a round
     * @param playerTurnStart a player starts his turn
     * @param playerTurnEnd a player ends hist turn
     * @param roundEnd the round ended
     * @param showMap show the maps
     * @param waitForEvents wait for player events
     */
    public void round(Executable playerTurnStart, Executable playerTurnEnd,
                      Executable roundEnd, Executable showMap,
                      Waiter<PlayerEvent> waitForEvents) {

        LOG.info("Round starting.");

        new Thread(() -> {

            try {

                Protocole.sendProtocol(out, 5, "START");

                receiveMaps(showMap);

                Protocole protocole = Protocole.receive(in);
                while (!protocole.getData().equals("END")) {

                    Protocole.sendProtocol(out, 5, "OK");

                    int id = Integer.parseInt(protocole.getData());

                    if (id == player.ID) {
                        player = Player.fromJson(Protocole.receiveJson(in));
                    }

                    LOG.info("Player " + id + "'s turn start");
                    playerTurnStart.execute(id);

                    if (id == player.ID) {

                        waitForEvents.waitData();

                        sendObject(objectOut,waitForEvents.receive());

                        //PlayerEvent.sendPlayerEvent(playerEvent, objectOut);
                    }

                    receiveMaps(showMap);

                    LOG.info("Player " + id + "'s turn end");
                    playerTurnEnd.execute();

                    protocole = Protocole.receive(in);
                }

                roundEnd.execute();

            } catch (IOException e) {
                quitGame();
            }

        }).start();
    }

    /**
     * Operate the start of the simulation
     * @param startSimulation starts the simulation
     */
    public void startSimulation(Executable startSimulation) {
        LOG.info("Waiting simulation");

        new Thread(() -> {

            Object o = null;
            try {
                Protocole.sendProtocol(out, 6, "START");
                o = Protocole.readObject(objectIn);

            } catch (SocketException e) {
                quitGame();
            }

            LOG.info("Simulation received");

            startSimulation.execute(o);

        }).start();
    }

    /**
     * Operates the end of the simulation
     * @param roundStart a round starts
     * @param gameEnd the game ended
     */
    public void endSimulation(Executable roundStart, Executable gameEnd) {

        LOG.info("Simulation done.");
        new Thread(() -> {

            try {
                Protocole.sendProtocol(out, 6, "OK");

                Protocole protocole = Protocole.receive(in);

                if (protocole.getId() == 600) {

                    roundStart.execute();

                } else if (protocole.getId() == 700) {

                    gameEnd.execute(Player.fromJson(Protocole.receiveJson(in)));
                }

            } catch (IOException e) {
                quitGame();
            }

        }).start();
    }

    /**
     * Gets the maps from the socket and shows them
     * @param showMaps shows the maps
     */
    private void receiveMaps(Executable showMaps) {

        String json =null;
        try {
             json = Protocole.receiveJson(in);
        } catch (SocketException e) {
            quitGame();
        }
        if (json == null) { return; }

        showMaps.execute(json);
    }

    /**
     * Gets the player
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets an array containt the id's of the opponents
     * @return opponents id's
     */
    public int[] getOpponentsIds() {

        int[] ids = new int[PLAYERS_COUNT - 1];
        int index = 0;

        for (int i = 0; i < PLAYERS_COUNT; ++i) {
            if (i != player.ID) {
                ids[index] = i;
                index++;
            }
        }

        return ids;
    }

    /**
     * Closes the connection
     */
    public void quitGame() {
        System.out.println("Leaving the game");
        close();
        if (game != null) {
            game.quit();
        }
    }

    /**
     * Closes the connection
     */
    public void close() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the game
     * @param game
     */
    public void setGame(TowerDefense game) {
        this.game = game;
    }
}
