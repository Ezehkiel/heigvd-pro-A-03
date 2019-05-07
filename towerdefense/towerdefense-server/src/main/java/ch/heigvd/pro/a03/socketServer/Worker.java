package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.utils.Protocole;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Logger;

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
    private static final int protcoleId=1;
    int nbPlayer;
    public Worker(Socket socket) {
        this.socket = socket;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        nbPlayer =0;
    }

    @Override
    public void run() {
        try {
            if(in.readLine().equals("100-START")){
                sendProtocol(out,1,"OK");
                int gameMode = Integer.parseInt(Protocole.receive(in).getData());
                sendProtocol(out,1,"OK");
                sendProtocol(out,1,"END");
                while (!in.readLine().equals("200-START"));
                Player p = new Player(socket);

                GameServer server = null;
                for (GameServer s : servers.get(gameMode)) {
                    if (s.players.size() < gameMode) {
                        server = s;
                        LOG.info(String.format("Add player for server with game mode %d", gameMode));
                        break;
                    }
                }

                if (server == null) {

                    LOG.info(String.format("New server with game mode %d", gameMode));
                    server = new GameServer(gameMode);
                    servers.get(gameMode).add(server);
                }

                server.playerJoin(p);

//                if(waitingList.get(gameMode).size()==1) {
//                    sendProtocol(out,2,"WAITINGPLAYER");
//                }else{
//                    sendProtocol(out,2,"PLAYERFOUND");
//                }
//                while (!canLaunchaGame(gameMode));
//                new Thread(new GameServer(waitingList.get(gameMode))).start();
//                sendProtocol(out, 2, "END");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
