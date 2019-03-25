package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.Protocole;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        switch (readProtocole(player)){
            case Protocole.CLIENTWANTPLAYMULTI:
                if(multiplayerQueue.isEmpty()){
                    multiplayerQueue.add(player);
                }else{
                    ArrayList<Player> match = new ArrayList<>();
                    match.add(multiplayerQueue.remove());
                    match.add(player);
                    new Thread(new GameServer(new ArrayList<>(match))).start();
                }
                break;
            case Protocole.CLIENTWANTPLAYSOLO:

                ArrayList<Player> match = new ArrayList<>();
                match.add(player);
               // match.add(ai)
                new Thread(new GameServer(new ArrayList<>(match))).start();
                break;
             default:
                 try {
                     throw new Exception("Bad protocol sent");
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
        }


    }
    int readProtocole(Player p){
        int receivedProtocole=-1;
        int checker=0;
        try{
            int data = p.in.read();
            while(data != 65535){
                checker++;
                receivedProtocole = data;
                data = p.in.read();
            }

            if(checker>1)
                throw new IOException("To much data transfered");

        }catch (IOException e){
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);
        }
        return receivedProtocole;
    }
}
