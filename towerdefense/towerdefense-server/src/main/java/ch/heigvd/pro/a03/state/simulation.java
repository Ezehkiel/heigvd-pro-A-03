package ch.heigvd.pro.a03.state;

import ch.heigvd.pro.a03.GameServer;
import ch.heigvd.pro.a03.MultiThreadedServer;

public class simulation implements ServerState {

    GameServer srv;

    public simulation(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

    }
}
