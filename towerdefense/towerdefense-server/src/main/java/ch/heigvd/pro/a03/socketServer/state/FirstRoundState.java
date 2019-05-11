package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.event.player.SendUnitEvent;
import ch.heigvd.pro.a03.event.player.UnitEvent;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Client;
import ch.heigvd.pro.a03.warentities.units.Unit;

import static ch.heigvd.pro.a03.event.player.PlayerEvent.getPlayerEvent;
import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;


public class FirstRoundState extends ServerState{
    public FirstRoundState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() {
        GameLogic gameLogic = gameServer.getGameLogic();

        // Broadcast the maps
        gameServer.broadCastJson(gameLogic.getMapsJson());

        for(Client client : gameServer.getClients()) {

            GameServer.LOG.info("Player " + client.getPlayer().ID + "'s first turn.");

            // Tell everyone who's turn it is
            gameServer.broadCastMessage(String.valueOf(client.getPlayer().ID));

            // Wait for the player's events
            PlayerEvent playerEvent = getPlayerEvent(client.getOis());
            for(UnitEvent unitEvent : playerEvent.getUnitEvents()) {
                SendUnitEvent sendUnitEvent = (SendUnitEvent) unitEvent;
                for (int i = 0; i < sendUnitEvent.getQuantity(); ++i) {
                    Unit unit = unitEvent.getUnitType().createUnit(
                            gameLogic.getPlayerMap(client.getPlayer().ID).getSpawnPoint()
                    );
                    gameLogic.getPlayerMap(sendUnitEvent.getPlayerIdDestination()).addUnit(unit);
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
