package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.utils.Protocole;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Communication.readProtocol;

class MatchMaker implements Runnable {

    static  Queue<Player> multiplayerQueue = new LinkedList<>();
    Socket socket;



    public MatchMaker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Player player = new Player(socket);
        SocketServer.connectedPlayer.add(player);
        try {
            switch (readProtocol(player.in)) {
                case Protocole.CLIENTWANTPLAYMULTI:
                    if (multiplayerQueue.isEmpty()) {
                        multiplayerQueue.add(player);
                    } else {
                        ArrayList<Player> match = new ArrayList<>();
                        match.add(multiplayerQueue.remove());
                        match.add(player);
                        new Thread(new GameServer(new ArrayList<>(match))).start();
                    }
                    break;
                case Protocole.CLIENTWANTPLAYSOLO:

                    ArrayList<Player> match = new ArrayList<>();
                    match.add(player);
                    new Thread(new GameServer(new ArrayList<>(match))).start();
                    break;
                default:
                    try {
                        throw new Exception("Bad protocol sent");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }catch (IOException e){
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);
        }


    }

}
