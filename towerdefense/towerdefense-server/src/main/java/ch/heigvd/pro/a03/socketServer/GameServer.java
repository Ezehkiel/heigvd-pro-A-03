package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.socketServer.state.*;
import ch.heigvd.pro.a03.utils.Protocole;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Protocole.*;

/**
 * Main class of the server part of the game
 */
public class GameServer implements Runnable {

    public static Logger LOG = Logger.getLogger(GameServer.class.getSimpleName());

    // list of all stats
    public ServerState ValidationState;
    public ServerState FirstRoundState;
    public ServerState RoundState;
    public ServerState SimulationState;
    public ServerState EndState;

    public ServerState currentState;

    public final int PLAYER_COUNT;
    private int arrivedPlayersCount;
    private Client[] clients;

    private GameLogic gameLogic;

    /**
     * As explain in the documentation, the unit are sent ton the client only the next round
     * So we have a list to track the player's units
     */
    public LinkedList<LinkedList<Unit>> nextRoundUnit;

    public GameServer(int gameMode) {
        this.PLAYER_COUNT = gameMode;
        this.arrivedPlayersCount = 0;
        this.clients = new Client[PLAYER_COUNT];
        this.gameLogic = null;
        this.ValidationState = new ValidationState(3, this);
        this.FirstRoundState = new FirstRoundState(4, this);
        this.RoundState = new RoundState(5, this);
        this.SimulationState = new SimulationState(6, this);
        this.EndState = new EndState(7, this);
        // Add state
        currentState = new ServerState(2, this) {
            @Override
            public void run() {
            }
        };
        nextRoundUnit  = new LinkedList<>();

        initNextRoundList();
    }


    public int getClientsCount() {
        return arrivedPlayersCount;
    }

    public Client[] getClients() {
        return clients;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    /**
     * initilise the list
     */
    public void initNextRoundList() {
        for(int i=0; i < PLAYER_COUNT;i++){
            nextRoundUnit.add(new LinkedList<>());
        }
    }

    /**
     * Fonction is there to say to the connected clients that a new player join
     * And the information to new client about the connected clients
     * @param client
     * @param name
     */
    public void playerJoin(Client client, String name) throws SocketException {

        LOG.info("A client joined a game server!");

        Player player = new Player(arrivedPlayersCount, name);
        client.setPlayer(player);

        for (int i = 0; i < arrivedPlayersCount; ++i) {

            // send newcomer to other players
            Protocole.sendJson(player.toJson(), clients[i].getOut());

            // send already present player to newcomer
            Protocole.sendJson(clients[i].getPlayer().toJson(), client.getOut());
        }

        clients[arrivedPlayersCount++] = client;

        if (arrivedPlayersCount == PLAYER_COUNT) {

            Player[] players = new Player[PLAYER_COUNT];
            for (Client c : clients) {

                // Tell the client that everyone arrived
                Protocole.sendProtocol(c.getOut(), 2, "OK");

                // Tell the client which player he is
                Protocole.sendJson(c.getPlayer().toJson(), c.getOut());

                players[c.getPlayer().ID] = c.getPlayer();
            }

            LOG.info("A game server has started!");
            gameLogic = new GameLogic(players);

            new Thread(this).start();

            SocketServer.getInstance().gameServers.add(this);
        }
    }

    @Override
    public void run() {
        try {
            this.setCurrentState(ValidationState);
        } catch (SocketException e) {
            System.out.println("broadcast End game");
            try {
                broadCastProtocol("CLIENTDISCONNECTED");
            } catch (SocketException ex) {
                ex.printStackTrace();
            }
            for (Client p : this.getClients()){
                p.closeStreams();
            }
        }
        int  index = SocketServer.getInstance().gameServers.indexOf(this);

        SocketServer.getInstance().gameServers.remove(index);
        LOG.info("Game ended");
    }

    /**
     * It syncronise the changment sate between the client and the sever based on the protocol
     * @param newState
     */
    public void setCurrentState(ServerState newState) throws SocketException {
        broadCastProtocol("END");

        // HACKS
        LOG.info("Wait for clients to change state from " + currentState.getId() + " to " + newState.getId());
        waitForPlayers(newState.getId() + "00-START");

        this.currentState = newState;

        LOG.info("State changed to " + currentState.getId());

        currentState.run();
    }

    /**
     * Send a given message to all player in game
     * @param message
     */
    public void broadCastProtocol(String message) throws SocketException {
        for (Client client : clients)
            sendProtocol(client.getOut(), currentState.getId(), message);
    }
    /**
     * Send a given json to all player in game
     * @param json
     */
    public void broadCastJson(String json) throws SocketException {
        for (Client client : clients)
            sendJson(json, client.getOut());
    }
    /**
     * Send a given Object to all player in game throug ObjectOutputStream
     * @param object
     */
    public void broadCastObject(Object object) throws SocketException {
        for (Client client : clients) {
            sendObject(client.getOus(), object);
        }
    }

    /**
     * This fonciton is used to wait that all client response the given messge
     * It use the Waiter object
     * @param message
     */
    public void waitForPlayers(String message) {
        Thread t[] = new Thread[PLAYER_COUNT];
        //We cretae a new thread for he player
        for (Client client : clients) {
            t[client.getPlayer().ID] = new Thread(new waiter(client, message));
            t[client.getPlayer().ID].start();
        }

        // Wait that all thrad finished
        for (Client client : clients) {
            try {
                t[client.getPlayer().ID].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The waiter object is a runnble object that launch a function on a thread to wait a message
     */
    private class waiter implements Runnable {
        String response;
        Client client;

        public waiter(Client client, String response) {
            this.response = response;
            this.client = client;
        }

        @Override
        public void run() {

            while (true) {
                try {
                    if (!Protocole.receive(client.in).getData().equals(response)) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            GameServer.LOG.info(String.format("Player %d is ready.", client.getPlayer().ID));
        }
    }
}
