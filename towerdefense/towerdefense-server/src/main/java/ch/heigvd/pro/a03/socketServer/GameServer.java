package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.socketServer.state.*;
import ch.heigvd.pro.a03.utils.Protocole;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;


public class GameServer implements Runnable {

    public static Logger LOG = Logger.getLogger(GameServer.class.getSimpleName());

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

    public void playerJoin(Client client, String name) {

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
        }
    }

    @Override
    public void run() {
        this.setCurrentState(ValidationState);
        LOG.info("Game ended");
    }

    public ServerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ServerState newState) {
        broadCastMessage("END");

        // HACKS
        LOG.info("Wait for clients to change state from " + currentState.getId() + " to " + newState.getId());
        waitForPlayers(newState.getId() + "00-START");

        this.currentState = newState;

        LOG.info("State changed to " + currentState.getId());

        currentState.run();
    }

    public void broadCastMessage(String message) {
        for (Client client : clients)
            sendProtocol(client.getOut(), currentState.getId(), message);
    }

    public void broadCastJson(String json) {
        for (Client client : clients) {
            Protocole.sendJson(json, client.getOut());
        }
    }

    public void broadCastObject(Object object) {
        for (Client client : clients) {
            sendObject(client.getOus(), object);
        }
    }

    public void waitForPlayers(String message) {
        Thread t[] = new Thread[PLAYER_COUNT];
        for (Client client : clients) {
            t[client.getPlayer().ID] = new Thread(new waiter(client, message));
            t[client.getPlayer().ID].start();
        }

        for (Client client : clients) {
            try {
                t[client.getPlayer().ID].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendObject(ObjectOutputStream ous, Object object) {
        try {
            ous.writeObject(object);
            ous.flush();
//            ous.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                }
            }

            GameServer.LOG.info(String.format("Player %d is ready.", client.getPlayer().ID));
        }
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
    public void initNextRoundList(){
        for(int i=0; i < PLAYER_COUNT;i++){
            nextRoundUnit.toArray()[i] = new LinkedList<>();
        }
    }
}
