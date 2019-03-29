package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;

public class simulation implements ServerState {

    GameServer srv;

    public simulation(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

    }
}
