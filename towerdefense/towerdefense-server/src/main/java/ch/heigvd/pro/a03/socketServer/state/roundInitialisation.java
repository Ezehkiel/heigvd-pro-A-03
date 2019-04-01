package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.utils.Protocole;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Player;
import ch.heigvd.pro.a03.socketServer.SocketServer;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Communication.*;


// TODO : Send units - units -> change state to round
public class roundInitialisation implements ServerState {
    GameServer srv;


    public roundInitialisation(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {
        for(Player p :srv.getPlayers()){
            writeProtocol(p.getOut(), Protocole.SERVERINSTATUSINITIALISATION);
        }
        try{

            for(Player p :srv.getPlayers()){
                ArrayList<Unit> unitFromClient = readJson(p.getIn(),Unit.class);

                System.out.println("+++++" +unitFromClient);
            }
        }catch (IOException e){
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);

        }

        srv.setCurrentState(srv.round);
    }
}
