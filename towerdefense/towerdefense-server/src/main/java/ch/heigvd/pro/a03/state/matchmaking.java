package ch.heigvd.pro.a03.state;

import ch.heigvd.pro.a03.MultiThreadedServer;

public class matchmaking implements ServerState {

    MultiThreadedServer srv;

    public matchmaking(MultiThreadedServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

    }
}
