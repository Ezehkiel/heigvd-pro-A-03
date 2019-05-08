package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.socketServer.state.*;
import ch.heigvd.pro.a03.utils.Protocole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;


public class GameServer implements Runnable{

    public static Logger LOG = Logger.getLogger(GameServer.class.getSimpleName());

    public ServerState ValidationState;
    public ServerState FirstRoundState;
    public ServerState RoundState;
    public ServerState SimulationState;
    public ServerState EndState;

    public ServerState currentState;

    private int gameMode;
    public ArrayList<Client> clients;

    public GameServer(int gameMode) {
        this.gameMode = gameMode;
        this.clients = new ArrayList<>();
        this.ValidationState = new ValidationState(3,this) ;
        this.FirstRoundState = new FirstRoundState(4,this) ;
        this.RoundState = new RoundState(5,this) ;
        this.SimulationState = new SimulationState(6,this) ;
        this.EndState = new EndState(7,this) ;
        // Add state
        currentState = new ServerState(2, this) {
            @Override
            public void run() {}
        };
    }

    public void playerJoin(Client client) {

        LOG.info("A client joined a game server!");

        broadCastMessage("PLAYERFOUND");
        for (Player p : clients){
            Player.sendPlayer(client,((Client)p).ous);
            Player.sendPlayer(p,client.ous);
        }

        client.ID = clients.size();
        clients.add(client);

        if (clients.size() == gameMode) {

            LOG.info("A game server has started!");
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        this.setCurrentState(ValidationState);
    }

    public ServerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ServerState newState) {
        broadCastMessage("END");

        // HACKS
        try {
            LOG.info("Wait for clients to change state from " + currentState.getId() + " to " + newState.getId());
            waitForPlayers(newState.getId()+"00-START");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.currentState = newState;

        LOG.info("State changed to " + currentState.getId());

        currentState.run();
    }
    public void broadCastMessage(String message){
        for(Client p : clients)
            sendProtocol(p.getOut(),currentState.getId(),message);
    }
    public void waitForPlayers(String message) throws InterruptedException {
        Thread t[] = new Thread[clients.size()];
        for(Client p : clients){
            t[p.ID] = new Thread(new waiter(p,message));
            t[p.ID].start();
        }

        for(Client p : clients){
            t[p.ID].join();
        }
    }

    private class waiter implements Runnable{
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
                    if (!Protocole.receive(client.getIn()).getData().equals(response)) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            GameServer.LOG.info(String.format("Client %d is ready.", client.getId()));
        }
    }
}
