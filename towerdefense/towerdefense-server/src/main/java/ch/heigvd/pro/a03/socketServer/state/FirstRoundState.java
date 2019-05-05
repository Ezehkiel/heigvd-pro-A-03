package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Player;

import static ch.heigvd.pro.a03.utils.Communication.sendProtovol;

public class FirstRoundState extends ServerState{
    public FirstRoundState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() {

        for(Player p :gameServer.players){
            gameServer.broadCastMessage(String.valueOf(p.getId()));
            // WAIT UNITS
            sendProtovol(p.getOut(),gameServer.currentState.getId(),"OK");
        }
        gameServer.setCurrentState(gameServer.RoundState);
    }
}
