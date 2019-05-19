package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;

import java.net.SocketException;

/**
 *State is used to check that client is still there after the matchmaking
 */
public class ValidationState extends ServerState{

    public ValidationState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() throws SocketException {
        //Ask if there id ready
        gameServer.broadCastProtocol("READY");

        GameServer.LOG.info("Wait for clients to be ready.");

        gameServer.waitForPlayers(getId() + "00-YES");

        gameServer.setCurrentState(gameServer.FirstRoundState);
    }
}