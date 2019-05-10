package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.event.player.SendUnitEvent;
import ch.heigvd.pro.a03.event.player.TurretEvent;
import ch.heigvd.pro.a03.event.player.UnitEvent;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Client;

import java.awt.*;
import static ch.heigvd.pro.a03.event.player.PlayerEvent.getPlayerEvent;

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

            //gameServer.waitForPlayers("500-OK");d

            // send player to the client who's playing
            gameServer.sendObject(client.getOus(), client.getPlayer());

            // wait for player events
            PlayerEvent playerEvent =  getPlayerEvent(client.getOis());

            for(TurretEvent turretEvent : playerEvent.getTurretEvents()){
                Point position = turretEvent.getTurretPosition();
                gameLogic.getPlayerMap(client.getPlayer().ID).setStructureAt(turretEvent.getTurretType().createTurret(position),position.y,position.x);
            }
            //HACK cannot manage other type of event
            for(UnitEvent unitEvent : playerEvent.getUnitEvents()){
                gameLogic.getPlayerMap(((SendUnitEvent)unitEvent).getPlayerIdDestination()).addUnit(unitEvent.getUnitType().createUnit(gameLogic.getPlayerMap(client.getPlayer().ID).getSpawnPoint()));
            }

            GameServer.LOG.info("Received player " + client.getPlayer().ID + "'s events.");

            // Broadcast the new map
            gameServer.broadCastObject(gameLogic.getPlayerMap(client.getPlayer().ID));
        }

        gameServer.setCurrentState(gameServer.SimulationState);
    }
}