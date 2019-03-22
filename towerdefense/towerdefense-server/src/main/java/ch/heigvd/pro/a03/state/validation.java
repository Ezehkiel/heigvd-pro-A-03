package ch.heigvd.pro.a03.state;

import ch.heigvd.pro.a03.Client;
import ch.heigvd.pro.a03.GameServer;
import ch.heigvd.pro.a03.Protocole;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.awt.*;
import java.io.IOException;

public class validation implements ServerState {

    GameServer srv;
    public validation(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {

        for (Client c : srv.getClients()){
            this.request(c, Protocole.ISCLIENTREADY);
        }
        for (Client c : srv.getClients()){
            if(!c.isReady())
                try {
                    throw new Exception("Clien not ready");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            System.out.println("All client ready");
        }
        srv.setCurrentState(srv.firstRound);
    }
    void request(Client c,int protocolValue){
        //c.getOut().write(protocolValue);
        c.setReady(true);

    }
}
