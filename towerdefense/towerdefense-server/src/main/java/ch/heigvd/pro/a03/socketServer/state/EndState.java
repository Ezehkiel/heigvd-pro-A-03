package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;


public class EndState extends ServerState{
    public EndState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() {
        // Save the score to database
        gameServer.broadCastMessage("BYE");

    }
}
