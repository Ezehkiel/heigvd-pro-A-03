package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;

public class firstRound implements ServerState {
    GameServer srv;


    public firstRound(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {
        System.out.println("First round state");
    }
}
