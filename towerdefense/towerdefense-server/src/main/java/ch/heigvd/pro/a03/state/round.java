package ch.heigvd.pro.a03.state;

import ch.heigvd.pro.a03.MultiThreadedServer;

public class round implements ServerState{
    MultiThreadedServer srv;

    public round(MultiThreadedServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

    }
}
