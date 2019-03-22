package ch.heigvd.pro.a03.state;

import ch.heigvd.pro.a03.MultiThreadedServer;

public class noClient implements ServerState {
    MultiThreadedServer srv;

    public noClient(MultiThreadedServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

    }
}
