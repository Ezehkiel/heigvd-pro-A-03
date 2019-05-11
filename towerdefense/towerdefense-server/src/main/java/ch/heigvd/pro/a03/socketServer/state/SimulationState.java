package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.EventManager;
import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.socketServer.GameServer;
import com.google.gson.Gson;

public class SimulationState extends ServerState{
    public SimulationState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() {
        GameLogic gameLogic = this.gameServer.getGameLogic();

        gameLogic.playRound();
        gameLogic.getPlayerMap(1).getBase().isEntityDestroyed();

        if(gameLogic.getWinner() != null){
            gameServer.setCurrentState(gameServer.EndState);
        }else{
            gameServer.broadCastJson(new Gson().toJson(EventManager.getInstance().getEvents()));
            gameServer.setCurrentState(gameServer.RoundState);

        }
    }
}
