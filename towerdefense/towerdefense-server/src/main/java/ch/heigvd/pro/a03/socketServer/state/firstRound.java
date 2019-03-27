package ch.heigvd.pro.a03.socketServer.state;

import ch.heigvd.pro.a03.Protocole;
import ch.heigvd.pro.a03.socketServer.GameServer;
import ch.heigvd.pro.a03.socketServer.Player;
import ch.heigvd.pro.a03.socketServer.SocketServer;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.utils.Communication.*;

public class firstRound implements ServerState {
    GameServer srv;


    public firstRound(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {
        System.out.println("First round state");
        for(Player p :srv.getPlayers()){
            writeProtocol(p.getOut(), Protocole.SERVERINSTATUSFIRSTROUND);
        }

        try{
            ArrayList<Unit> unitFromA = readJson(srv.getPlayers().get(0).getIn(),Unit.class);
            System.out.println(unitFromA);

        }catch (IOException e){
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, e);

        }
    }
}
