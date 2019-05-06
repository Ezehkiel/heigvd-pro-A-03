package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Player;
import ch.heigvd.pro.a03.socketServer.SocketServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Protocole.*;
import static ch.heigvd.pro.a03.utils.Communication.readProtocol;
import static ch.heigvd.pro.a03.utils.Communication.writeProtocol;

public class validation implements ServerState {

    GameServer srv;
    public validation(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {
        ArrayList<Player> players = srv.getPlayers();
        for (Player p : players){
            try{
                writeProtocol(p.getOut(),ISCLIENTREADY);

                if(readProtocol(p.getIn())!= CLIENTREADY){
                    Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, "Player not ready");
                }
                //TODO : Gérér si un client est pas prêt
            }catch (IOException e){
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);
            }

        }
        Random rand = new Random();

        int playerOneIndex =rand.nextInt(players.size());
        int playerTwoIndex = (playerOneIndex != 0) ? 0:1;

        try {
            writeProtocol(players.get(playerOneIndex).getOut(), YOURAREPLAYERONE);
            writeProtocol(players.get(playerTwoIndex).getOut(), YOURAREPLAYERTWO);
        }catch (IOException e){
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);
        }

        srv.setCurrentState(srv.firstRound);
    }

}
