package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.event.Event;
import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Client;

import java.io.IOException;
import java.util.LinkedList;

import static ch.heigvd.pro.a03.event.player.PlayerEvent.getEvents;
import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;

public class RoundState extends ServerState{
    public RoundState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() {

        GameLogic gameLogic = gameServer.getGameLogic();

        // Broadcast the maps
        gameServer.broadCastObject(gameLogic.getMaps());

        for (Client client : gameServer.getClients()) {
            // Broadcast who's playing
            gameServer.broadCastMessage(String.valueOf(client.getPlayer().ID));

            // send player to the client who's playing
            gameServer.sendObject(client.getOus(), client.getPlayer());

            // wait for player events
            LinkedList<Event> playerEvents = getEvents(client.getOis());

            // TODO update the map
            GameServer.LOG.info("Received player " + client.getPlayer().ID + "'s events.");

            // Broadcast the new map
            gameServer.broadCastObject(gameLogic.getPlayerMap(client.getPlayer().ID));

            //sendProtocol(client.getOut(), gameServer.currentState.getId(), "OK");
        }

        gameServer.setCurrentState(gameServer.SimulationState);
    }
}