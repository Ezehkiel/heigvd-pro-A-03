package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;

public class round implements ServerState{
    GameServer srv;

    public round(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

    }
}
