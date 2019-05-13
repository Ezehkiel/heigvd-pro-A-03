package ch.heigvd.pro.a03.server;
import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.commands.Executable;
import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.event.simulation.SimEvent;
import ch.heigvd.pro.a03.utils.Protocole;
import ch.heigvd.pro.a03.utils.Waiter;

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
        HOST = "localhost";//"ezehkiel.ch";
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

                        PlayerEvent.sendPlayerEvent(waitForEvents.receive(), objectOut);

                        Protocole.receive(in);
                    }

                    protocole = Protocole.receive(in);

                    LOG.info("Player " + id + "'s turn end.");
                    playerTurnEnd.execute(id);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            roundEnd.execute();

        }).start();
    }

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

                        PlayerEvent playerEvent = waitForEvents.receive();
                        PlayerEvent.sendPlayerEvent(playerEvent, objectOut);
                    }

                    receiveMaps(showMap);

                    LOG.info("Player " + id + "'s turn end");
                    playerTurnEnd.execute();

                    protocole = Protocole.receive(in);
                }

                roundEnd.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void startSimulation(Executable startSimulation) {
        LOG.info("Waiting simulation");

        new Thread(() -> {

            Protocole.sendProtocol(out, 6, "START");

            Object o = (LinkedList<SimEvent>) receiveObject();

            LOG.info("Simulation received");

            startSimulation.execute(o);

        }).start();
    }

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
                e.printStackTrace();
            }

        }).start();
    }

    private void receiveMaps(Executable showMaps) {

        String json = Protocole.receiveJson(in);
        if (json == null) { return; }

        showMaps.execute(json);
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

    public Player getPlayer() {
        return player;
    }

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
}
