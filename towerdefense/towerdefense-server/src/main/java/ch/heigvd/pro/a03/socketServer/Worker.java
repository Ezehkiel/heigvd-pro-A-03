package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.Server;
import ch.heigvd.pro.a03.utils.Protocole;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Protocole.receive;
import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;

/**
 * The worker dispatch the player/client into game server
 */
public class Worker implements Runnable{

    private static Logger LOG = Logger.getLogger(Worker.class.getSimpleName());

    /**
     * We index a list of game servers by the gamemode
     */
    public static ArrayList<ArrayList<GameServer>> servers;
    public static ArrayList<GameServer> runningServer;

    // HACK : This is a bad array list initialisation
    static {
        servers = new ArrayList<>();
        servers.add(null);
        servers.add(null);
        servers.add(new ArrayList<>());
    }

    Socket socket;
    private BufferedWriter out;
    private BufferedReader in;

    /**
     * Constructor
     * @param socket the client socket
     * @param runningServer the liste of running server
     */
    public Worker(Socket socket, ArrayList<GameServer> runningServer) {
        this.socket = socket;
        this.runningServer= runningServer;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  The fonction runned in the thread
     */
    @Override
    public void run() {
        try {
            //Waiting client start the communication
            if(in.readLine().equals("100-START")){
                sendProtocol(out,1,"OK");
                //Get username
                Protocole prot = receive(in);
                sendProtocol(out,1,"OK");

                //Get the gameMode
                int gameMode = Integer.parseInt(Protocole.receive(in).getData());
                sendProtocol(out,1,"OK");

                sendProtocol(out,1,"END");

                //Start the matchmaking system
                while (!in.readLine().equals("200-START"));
                Client p = new Client(socket);

                GameServer server = null;
                //Search in the queue for a sever with a place for the new client
                for (GameServer s : servers.get(gameMode)) {
                    if (s.getClientsCount() < gameMode) {
                        server = s;
                        LOG.info(String.format("Add client for server with game mode %d", gameMode));
                        break;
                    }
                }
                // if there is no server we create a new one
                if (server == null) {

                    LOG.info(String.format("New server with game mode %d", gameMode));
                    server = new GameServer(gameMode);
                    servers.get(gameMode).add(server);
                }

                // the server will continue the managment
                server.playerJoin(p, prot.getData());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
