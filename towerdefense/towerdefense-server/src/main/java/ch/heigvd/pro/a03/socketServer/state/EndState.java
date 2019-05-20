package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;

import java.net.SocketException;


/**
 * The last state of the game that send goodbye to client
 * and finished the game because there is no setnextState()
 */
public class EndState extends ServerState{
    public EndState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() throws SocketException {
        // Save the score to database
        gameServer.broadCastProtocol("BYE");

    }
}
