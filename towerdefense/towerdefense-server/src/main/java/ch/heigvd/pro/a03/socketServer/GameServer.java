package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.socketServer.state.*;
import ch.heigvd.pro.a03.utils.Protocole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static ch.heigvd.pro.a03.utils.Communication.sendProtovol;


public class GameServer implements Runnable{

    public ServerState ValidationState;
    public ServerState FirstRoundState;
    public ServerState RoundState;
    public ServerState SimulationState;
    public ServerState EndState;

    public ServerState currentState;

    public ArrayList<Player> players;


    public GameServer(ArrayList<Player> players) {
        this.players =players;
        this.ValidationState = new ValidationState(3,this) ;
        this.FirstRoundState = new FirstRoundState(4,this) ;
        this.RoundState = new RoundState(5,this) ;
        this.SimulationState = new SimulationState(6,this) ;
        this.EndState = new EndState(7,this) ;
        // Add state
        currentState = null;
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

        try {
            waitForPlayers(newState.getId()+"-START");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.currentState = newState;
        currentState.run();
    }
    public void broadCastMessage(String message){
        for(Player p : players)
            sendProtovol(p.getOut(),currentState.getId(),message);
    }
    public void waitForPlayers(String message) throws InterruptedException {
        Thread t[] = new Thread[players.size()];
        for(Player p : players){
            t[p.id] = new Thread(new waiter(p.in,message));
            t[p.id].start();
        }

        for(Player p : players){
            t[p.id].join();
        }
    }

    private class waiter implements Runnable{
        String response;
        BufferedReader in;
        public waiter(BufferedReader in, String response) {
            this.response = response;
            this.in=in;
        }

        @Override
        public void run() {

            while (true) {
                try {
                    if (!Protocole.receive(in).getData().equals(response)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };

        }
    }
}
