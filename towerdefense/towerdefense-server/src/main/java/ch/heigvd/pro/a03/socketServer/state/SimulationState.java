package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;

public class SimulationState extends ServerState{
    public SimulationState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() {
        //do the simulation

        //check if there is a winner
        /*if(...){
            //Stream the result
            gameServer.setCurrentState(gameServer.RoundState);
        }else{
            gameServer.setCurrentState(gameServer.EndState);
        }*/
    }

}
