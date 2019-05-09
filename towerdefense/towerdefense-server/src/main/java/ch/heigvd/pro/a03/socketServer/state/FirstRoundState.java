package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.event.Event;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Client;

import java.util.LinkedList;

import static ch.heigvd.pro.a03.event.player.PlayerEvent.getEvents;
import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;


public class FirstRoundState extends ServerState{
    public FirstRoundState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() {

        for(Client client : gameServer.getClients()) {

            // Tell everyone who's turn it is
            gameServer.broadCastMessage(String.valueOf(client.getPlayer().ID));

            // Wait for the player's events
            LinkedList<Event> playerEvents = getEvents(client.getOis());

            // TODO update the map

            GameServer.LOG.info("Received player " + client.getPlayer().ID + "'s events.");

            // Tell everyone that the play has finished his turn
            sendProtocol(client.getOut(),gameServer.currentState.getId(),"OK");
        }

        gameServer.setCurrentState(gameServer.RoundState);
    }
}
