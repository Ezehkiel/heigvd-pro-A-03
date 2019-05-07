package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.socketServer.state.*;
import ch.heigvd.pro.a03.utils.Protocole;

import java.io.BufferedReader;
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
    public ArrayList<Player> players;

    public GameServer(int gameMode) {
        this.gameMode = gameMode;
        this.players = new ArrayList<>();
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

    public void playerJoin(Player player) {

        LOG.info("A player joined a game server!");

        // TODO send current player's infos to new player
        broadCastMessage("PLAYERFOUND"); // TODO send player infos

        player.id = players.size();
        players.add(player);

        if (players.size() == gameMode) {

            LOG.info("A game server has started!");

            broadCastMessage("END");
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
            LOG.info("Wait for players to change state from " + currentState.getId() + " to " + newState.getId());
            waitForPlayers(newState.getId()+"00-START");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.currentState = newState;

        LOG.info("State changed to " + currentState.getId());

        currentState.run();
    }
    public void broadCastMessage(String message){
        for(Player p : players)
            sendProtocol(p.getOut(),currentState.getId(),message);
    }
    public void waitForPlayers(String message) throws InterruptedException {
        Thread t[] = new Thread[players.size()];
        for(Player p : players){
            t[p.id] = new Thread(new waiter(p,message));
            t[p.id].start();
        }

        for(Player p : players){
            t[p.id].join();
        }
    }

    private class waiter implements Runnable{
        String response;
        Player player;
        public waiter(Player player, String response) {
            this.response = response;
            this.player = player;
        }

        @Override
        public void run() {

            while (true) {
                try {
                    if (!Protocole.receive(player.getIn()).getData().equals(response)) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            GameServer.LOG.info(String.format("Player %d is ready.", player.getId()));
        }
    }
}
