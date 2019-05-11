package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.event.Event;
import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.event.player.SendUnitEvent;
import ch.heigvd.pro.a03.event.player.UnitEvent;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Client;

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
        gameServer.broadCastObject(gameLogic.getMaps());

        for(Client client : gameServer.getClients()) {

            GameServer.LOG.info("Player " + client.getPlayer().ID + "'s first turn.") ;

            // Tell everyone who's turn it is
            gameServer.broadCastMessage(String.valueOf(client.getPlayer().ID));

            // Wait for the player's events
            PlayerEvent playerEvent =  getPlayerEvent(client.getOis());
            for(UnitEvent unitEvent : playerEvent.getUnitEvents()) {
                SendUnitEvent sendUnitEvent = (SendUnitEvent) unitEvent;
                for (int i = 0; i < sendUnitEvent.getQuantity(); ++i) {
                    gameLogic.getPlayerMap(sendUnitEvent.getPlayerIdDestination()).addUnit(
                            unitEvent.getUnitType().createUnit(
                                    gameLogic.getPlayerMap(client.getPlayer().ID).getSpawnPoint()
                            )
                    );
                }
            }

            GameServer.LOG.info("Received player " + client.getPlayer().ID + "'s events.");

            // Tell everyone that the play has finished his turn
            sendProtocol(client.getOut(),gameServer.currentState.getId(),"OK");
        }

        gameServer.setCurrentState(gameServer.RoundState);
    }
}
