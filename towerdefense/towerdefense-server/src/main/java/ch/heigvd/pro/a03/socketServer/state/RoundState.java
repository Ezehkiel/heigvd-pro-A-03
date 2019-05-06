package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Player;

import java.util.LinkedList;

import static ch.heigvd.pro.a03.event.player.PlayerEvent.getEvents;
import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;

public class RoundState extends ServerState{
    public RoundState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() {
        //Broadcast the map

        try {
            gameServer.waitForPlayers("OK");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Player p :gameServer.players){
            gameServer.broadCastMessage(String.valueOf(p.getId()));
            LinkedList<PlayerEvent> playerEvents = getEvents(p.getOis());
            // Send this to map

            sendProtocol(p.getOut(),gameServer.currentState.getId(),"OK");
        }

        gameServer.setCurrentState(gameServer.SimulationState);
    }
}