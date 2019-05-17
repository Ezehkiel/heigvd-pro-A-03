package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.EventManager;
import ch.heigvd.pro.a03.GameLogic;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.httpServer.HttpServer;
import ch.heigvd.pro.a03.httpServer.SqlRequest;
import ch.heigvd.pro.a03.socketServer.Client;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.users.User;
import ch.heigvd.pro.a03.utils.Protocole;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * The state call the game logic to calculate the simulation and send itto client
 */
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
            for(int i = 0; i<gameServer.PLAYER_COUNT; ++i){
                if(gameServer.getClients()[i].getPlayer().ID != loser.ID){
                    String serverToken = HttpServer.getInstance().getToken();
                    User loserUser = SqlRequest.getUserDBWithUsername(loser.getName());
                    User winnerUser =SqlRequest.getUserDBWithUsername(gameServer.getClients()[i].getPlayer().getName());
                    String data = "{\"token\": \"" + serverToken + "\", \"idWinner\": \"" +
                            winnerUser.getId()+ "\", \"idLoser\": \"" + loserUser.getId() + "\"}";
                    HttpURLConnection connection = null;
                    try {

                        connection = (HttpURLConnection) new URL("https://127.0.0.1:3945/users/scores").openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        OutputStream os = connection.getOutputStream();
                        os.write(data.getBytes());
                        os.flush();
                        os.close();


                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
                            );

                            StringBuilder responseBuilder = new StringBuilder();
                            String line;

                            while ((line = reader.readLine()) != null) {
                                responseBuilder.append(line);
                            }

                            System.out.println(responseBuilder.toString());

                            JSONObject response = new JSONObject(responseBuilder.toString());
                            boolean haveError = response.getBoolean("error");
                            String errorMessage;
                            if(haveError){
                                errorMessage = response.getString("message");
                            }
                        }



                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

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
