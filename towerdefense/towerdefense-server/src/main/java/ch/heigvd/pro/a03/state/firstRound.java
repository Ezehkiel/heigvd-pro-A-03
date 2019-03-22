package ch.heigvd.pro.a03.state;

import ch.heigvd.pro.a03.MultiThreadedServer;

public class firstRound implements ServerState {
    MultiThreadedServer srv;

    public firstRound(MultiThreadedServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

    }
}
