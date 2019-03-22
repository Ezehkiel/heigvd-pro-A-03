package ch.heigvd.pro.a03.state;

import ch.heigvd.pro.a03.GameServer;
import ch.heigvd.pro.a03.MultiThreadedServer;

public class round implements ServerState{
    GameServer srv;

    public round(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

    }
}
