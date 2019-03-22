package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.state.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer implements Runnable{

    // Liste des sockets de joueur (Max 2) mais on pourrait imaginer jouer
    ArrayList<Client> clients = new ArrayList<>();

    public ServerState validation;
    public ServerState firstRound;
    public ServerState round;
    public ServerState simulation;

    public ServerState currentState;


    public GameServer(ArrayList<Socket> clientSockets) {

        for(Socket s : clientSockets){
            clients.add(new Client(s));
        }

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

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ServerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ServerState newState) {
        System.out.println("Changing state");
        this.currentState = newState;
        currentState.master();
    }
}
