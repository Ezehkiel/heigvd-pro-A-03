package ch.heigvd.pro.a03.state;

import ch.heigvd.pro.a03.MultiThreadedServer;

public class simulation implements ServerState {
    MultiThreadedServer srv;

    public simulation(MultiThreadedServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

    }
}
