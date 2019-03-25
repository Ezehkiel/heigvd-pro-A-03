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
    int clientId =0;
    public validation(GameServer srv) {
        this.srv = srv;
    }

    @Override
    public void master() {
        for (Client c : srv.getClients()){
            clientId++;
            this.request(c, Protocole.ISCLIENTREADY);
            if(!c.isReady())
                try {
                    throw new Exception("Client not ready");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            System.out.println("client" + clientId+ " ready");

        }
        srv.setCurrentState(srv.firstRound);
    }
    void request(Client c,int protocolValue){
        System.out.println("Request Client"+ clientId);
        try{
            System.out.println("Server write protocol :" + protocolValue);
            c.getOut().write(protocolValue);
            c.getOut().flush();
            System.out.println("Server write end of transmission : -1");
            c.getOut().write(-1);
            c.getOut().flush();
            System.out.println("SRV writed");

            System.out.println("Server wait for client"+ clientId +" response");

            int data = c.getIn().read();
            while(data != 65535){
                data = c.getIn().read();
            }
            System.out.println("Server get response of client" + clientId);

        }catch (IOException e){
            System.out.println("oksjdbvoyj");
        }
        //c.getOut().write(protocolValue);
        c.setReady(true);

    }
}
