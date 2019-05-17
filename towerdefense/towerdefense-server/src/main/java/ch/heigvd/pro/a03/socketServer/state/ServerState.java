package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;

/**
 * Clas that defined how a state is structured
 */
public abstract class ServerState {

    private int id;

    GameServer gameServer;

    public ServerState(int id, GameServer gameServer) {
        this.id = id;
        this.gameServer = gameServer;
    }

    public int getId() {
        return id;
    }

    public abstract void run();
}
