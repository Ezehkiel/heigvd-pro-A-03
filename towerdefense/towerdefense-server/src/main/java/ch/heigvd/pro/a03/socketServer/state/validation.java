package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.Protocole;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Player;
import ch.heigvd.pro.a03.socketServer.SocketServer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class validation implements ServerState {

    GameServer srv;
    public validation(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {
        for (Player p : srv.getPlayers()){
            if(!isPlayerReady(p)){
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, "Player not ready ");
            }
            //TODO : Gérér si un client est pas prêt
        }
        srv.setCurrentState(srv.firstRound);
    }
    boolean isPlayerReady(Player p){
        int receivedProtocole=-1;

        try{
            p.getOut().write(Protocole.ISCLIENTREADY);
            p.getOut().flush();
            p.getOut().write(-1);
            p.getOut().flush();
            int data = p.getIn().read();
            while(data != 65535){
                receivedProtocole=data;
                data = p.getIn().read();
            }
        }catch (IOException e){
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);
        }

        return receivedProtocole == Protocole.CLIENTREADY;
    }
}
