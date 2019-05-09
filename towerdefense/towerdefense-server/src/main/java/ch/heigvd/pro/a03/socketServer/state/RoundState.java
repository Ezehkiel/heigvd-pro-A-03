package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.event.Event;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Client;

import java.util.LinkedList;

import static ch.heigvd.pro.a03.event.player.PlayerEvent.getEvents;

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

            GameServer.LOG.info("Player " + client.getPlayer().ID + "'s turn.") ;

            // Broadcast who's playing
            gameServer.broadCastMessage(String.valueOf(client.getPlayer().ID));

            //gameServer.waitForPlayers("500-OK");

            // send player to the client who's playing
            gameServer.sendObject(client.getOus(), client.getPlayer());

            // wait for player events
            LinkedList<Event> playerEvents = getEvents(client.getOis());

            // TODO update the map

            GameServer.LOG.info("Received player " + client.getPlayer().ID + "'s events.");

            // Broadcast the new map
            gameServer.broadCastObject(gameLogic.getPlayerMap(client.getPlayer().ID));
        }

        gameServer.setCurrentState(gameServer.SimulationState);
    }
}