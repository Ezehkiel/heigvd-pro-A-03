package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.event.player.SendUnitEvent;
import ch.heigvd.pro.a03.event.player.UnitEvent;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Client;
import ch.heigvd.pro.a03.socketServer.SocketServer;
import ch.heigvd.pro.a03.warentities.WarEntity;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.net.SocketException;

//import static ch.heigvd.pro.a03.event.player.PlayerEvent.getPlayerEvent;
import static ch.heigvd.pro.a03.utils.Protocole.readObject;
import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;


/**
 * The first round state ask the client his units
 */
public class FirstRoundState extends ServerState{

    public FirstRoundState(int id, GameServer gameServer) {
        super(id, gameServer);
    }



    @Override
    public void run() throws SocketException {
        GameLogic gameLogic = gameServer.getGameLogic();

        System.out.println(SocketServer.getInstance().getGameServers().size());

        // Broadcast the maps
        gameServer.broadCastJson(gameLogic.getMapsJson());

        for(Client client : gameServer.getClients()) {

            GameServer.LOG.info("Player " + client.getPlayer().ID + "'s first turn.");

            // Tell everyone who's turn it is
            gameServer.broadCastProtocol(String.valueOf(client.getPlayer().ID));

            // Wait for the player's events
            PlayerEvent playerEvent = (PlayerEvent) readObject(client.getOis());
            for(UnitEvent unitEvent : playerEvent.getUnitEvents()) {
                SendUnitEvent sendUnitEvent = (SendUnitEvent) unitEvent;
                for (int i = 0; i < sendUnitEvent.getQuantity(); ++i) {
                    Unit unit = unitEvent.getUnitType().createUnit(
                            gameLogic.getPlayerMap(client.getPlayer().ID).getSpawnPoint()
                    );
                    ((WarEntity) unit).setId(gameLogic.getNextEntityId());
                    gameServer.nextRoundUnit.get(sendUnitEvent.getPlayerIdDestination()).push(unit);
                    client.getPlayer().removeMoney(unit.getPrice());
                }
            }

            GameServer.LOG.info("Received player " + client.getPlayer().ID + "'s events.");

            // Tell everyone that the player has finished his turn
            sendProtocol(client.getOut(),gameServer.currentState.getId(),"OK");
        }

        gameLogic.giveMoneyToPlayers();
        gameServer.setCurrentState(gameServer.RoundState);
    }
}
