package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.utils.Protocole;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Protocole.receive;
import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;

public class Worker implements Runnable{

    private static Logger LOG = Logger.getLogger(Worker.class.getSimpleName());
    public static ArrayList<ArrayList<GameServer>> servers;

    static {
        servers = new ArrayList<>();
        servers.add(null);
        servers.add(null);
        servers.add(new ArrayList<>());
    }

    Socket socket;
    private BufferedWriter out;
    private BufferedReader in;

    public Worker(Socket socket) {
        this.socket = socket;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            if(in.readLine().equals("100-START")){
                sendProtocol(out,1,"OK");
                //Get username
                Protocole prot = receive(in);
                sendProtocol(out,1,"OK");

                int gameMode = Integer.parseInt(Protocole.receive(in).getData());
                sendProtocol(out,1,"OK");

                sendProtocol(out,1,"END");
                while (!in.readLine().equals("200-START"));
                Client p = new Client(socket);

                GameServer server = null;
                for (GameServer s : servers.get(gameMode)) {
                    if (s.getClientsCount() < gameMode) {
                        server = s;
                        LOG.info(String.format("Add client for server with game mode %d", gameMode));
                        break;
                    }
                }

                if (server == null) {

                    LOG.info(String.format("New server with game mode %d", gameMode));
                    server = new GameServer(gameMode);
                    servers.get(gameMode).add(server);
                }

                server.playerJoin(p, prot.getData());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
