package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.socketServer.state.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServer implements Runnable{

    // Liste des sockets de joueur (Max 2) mais on pourrait imaginer jouer à beaucoup plus
    ArrayList<Player> players;
    public ServerState validation;
    public ServerState firstRound;
    public ServerState round;
    public ServerState simulation;

    public ServerState currentState;


    public GameServer(ArrayList<Player> players) {

        this.players = players;

        validation = new validation(this);
        firstRound = new firstRound(this);
        round = new round(this);
        simulation = new simulation(this);

        currentState = validation;
    }


    @Override
    public void run() {
        currentState.master();
    }


    public ServerState getCurrentState() {
        return currentState;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setCurrentState(ServerState newState) {
        System.out.println("Changing state");
        this.currentState = newState;
        currentState.master();
    }

}
