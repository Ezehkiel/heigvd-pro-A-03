package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.EventManager;
import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.socketServer.Client;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.utils.Protocole;
import com.google.gson.Gson;

public class SimulationState extends ServerState {
    public SimulationState(int id, GameServer gameServer) {
        super(id, gameServer);
    }

    @Override
    public void run() {
        GameLogic gameLogic = this.gameServer.getGameLogic();

        GameServer.LOG.info("Simulating ...");
        gameLogic.playRound();

        gameServer.broadCastObject(EventManager.getInstance().getEvents());

        GameServer.LOG.info("Simulation sent. Waiting for players ...");
        gameServer.waitForPlayers("600-OK");

        Player loser = gameLogic.getLoser();
        if (loser != null) {
            GameServer.LOG.info("There is a loser.");

            for (Client client : gameServer.getClients()) {
                Protocole.sendProtocol(client.getOut(), 7, "END");
            }

            gameServer.broadCastJson(loser.toJson());

        } else {
            GameServer.LOG.info("Game continues.");
            gameServer.setCurrentState(gameServer.RoundState);
        }
    }
}
