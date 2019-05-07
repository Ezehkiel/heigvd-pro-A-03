package ch.heigvd.pro.a03.socketServer;

import ch.heigvd.pro.a03.utils.Protocole;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static ch.heigvd.pro.a03.utils.Protocole.sendProtocol;

public class Worker implements Runnable{
    static ArrayList<ArrayList<Player>> waitingList;

    static {
        waitingList = new ArrayList<>();
        waitingList.add(null);
        waitingList.add(null);
        waitingList.add(new ArrayList<>());
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
                waitingList.get(gameMode).add(p);
                if(waitingList.get(gameMode).size()==1){
                    sendProtocol(out,2,"WAITINGPLAYER");
                }else{
                    sendProtocol(out,2,"PLAYERFOUND");
                }
                while (!canLaunchaGame(gameMode));
                new Thread(new GameServer(waitingList.get(gameMode))).start();
                waitingList.get(gameMode).clear();
                sendProtocol(out, 2, "END");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean canLaunchaGame(int mode) {
        return waitingList.get(mode).size() == mode;
    }

    public int getNbPlayer() {
        return nbPlayer;
    }
}
