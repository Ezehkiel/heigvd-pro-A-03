package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.actions.PlayerEvent;
import ch.heigvd.pro.a03.utils.Protocole;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Player;
import ch.heigvd.pro.a03.socketServer.SocketServer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Communication.*;

public class roundInitialisation implements ServerState {
    GameServer srv;


    public roundInitialisation(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

        try{
            for(Player p :srv.getPlayers()){
                writeProtocol(p.getOut(), Protocole.SERVERINSTATUSINITIALISATION);
            }
            for(Player p :srv.getPlayers()){
                PlayerEvent unitFromClient = readPlayerEventFromJson(p.getIn());
                System.out.println(unitFromClient);
            }
        }catch (IOException e){
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);

        }

        srv.setCurrentState(srv.round);
    }
}



// return toJson("error :" + true.t
