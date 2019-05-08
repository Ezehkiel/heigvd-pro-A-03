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
            gameServer.broadCastMessage(String.valueOf(client.getPlayer().ID));
            LinkedList<Event> playerEvents = getEvents(client.getOis());
            // Send this to map
            sendProtocol(client.getOut(),gameServer.currentState.getId(),"OK");
        }
        gameServer.setCurrentState(gameServer.RoundState);
    }
}
