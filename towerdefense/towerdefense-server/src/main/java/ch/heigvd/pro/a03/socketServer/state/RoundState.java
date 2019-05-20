package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.event.player.SendUnitEvent;
import ch.heigvd.pro.a03.event.player.TurretEvent;
import ch.heigvd.pro.a03.event.player.UnitEvent;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Client;
import ch.heigvd.pro.a03.utils.Protocole;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;
import java.net.SocketException;

import static ch.heigvd.pro.a03.utils.Protocole.readObject;

//import static ch.heigvd.pro.a03.event.player.PlayerEvent.getPlayerEvent;

/**
 * The normal round state, it ask the unit and the turrent to all client
 */
public class RoundState extends ServerState{

    public RoundState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() throws SocketException {

        GameLogic gameLogic = gameServer.getGameLogic();

        // Send the previous round's unit
        for (Client c : gameServer.getClients()) {

            Map map = gameLogic.getPlayerMap(c.getPlayer().ID);
            for (Unit unit : gameServer.nextRoundUnit.get(c.getPlayer().ID)) {
                map.addUnit(unit);
            }
        }
        gameServer.nextRoundUnit.clear();
        gameServer.initNextRoundList();

        // Broadcast the maps
        gameServer.broadCastJson(gameLogic.getMapsJson());

        for (Client client : gameServer.getClients()) {


            GameServer.LOG.info("Player " + client.getPlayer().ID + "'s turn.");

            // Broadcast who's playing
            gameServer.broadCastProtocol(String.valueOf(client.getPlayer().ID));

            gameServer.waitForPlayers("500-OK");

            // send player to the client who's playing
            Protocole.sendJson(client.getPlayer().toJson(), client.getOut());

            // wait for player events
            PlayerEvent playerEvent = (PlayerEvent) readObject(client.getOis());

            for(TurretEvent turretEvent : playerEvent.getTurretEvents()) {

                Map map = gameLogic.getPlayerMap(client.getPlayer().ID);

                Point position = turretEvent.getTurretPosition();
                Turret turret = turretEvent.getTurretType().createTurret(position);
                turret.setId(gameLogic.getNextEntityId());

                map.setStructureAt(turret, position.y, position.x);
                client.getPlayer().removeMoney(turret.getPrice());
            }

            //HACK cannot manage other type of event
            for(UnitEvent unitEvent : playerEvent.getUnitEvents()) {

                SendUnitEvent sendUnitEvent = (SendUnitEvent) unitEvent;
                Map map = gameLogic.getPlayerMap(sendUnitEvent.getPlayerIdDestination());
                for (int i = 0; i < sendUnitEvent.getQuantity(); ++i) {
                    Unit unit = unitEvent.getUnitType().createUnit(map.getSpawnPoint());
                    unit.setId(gameLogic.getNextEntityId());
                    gameServer.nextRoundUnit.get(sendUnitEvent.getPlayerIdDestination()).push(unit);
                    client.getPlayer().removeMoney(unit.getPrice());
                }
            }

            GameServer.LOG.info("Received player " + client.getPlayer().ID + "'s events.");

            // Broadcast the new map
            gameServer.broadCastJson(gameLogic.getMapsJson());
        }

        gameServer.setCurrentState(gameServer.SimulationState);
    }
}