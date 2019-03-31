package ch.heigvd.pro.a03;


import ch.heigvd.pro.a03.utils.Communication;
import ch.heigvd.pro.a03.warentities.units.Scoot;
import ch.heigvd.pro.a03.warentities.units.Unit;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


public class FakeClient {
    static final String serverName = "localhost";
    static final int serverPort = 4567;
    static OutputStreamWriter out;
    static InputStreamReader in;

    public static void main (String ... args) throws Exception {
        System.out.println("Client start");

        // On créer le socket
        Socket socket = new Socket(serverName, serverPort);

        // Créer l'output Stream
        out = new OutputStreamWriter(socket.getOutputStream());
        out.flush();

        in = new InputStreamReader(socket.getInputStream());

        Communication.writeProtocol(out,Protocole.CLIENTWANTPLAYMULTI);

        System.out.println("Client wait server validation");
        int protocole = Communication.readProtocol(in);


        if(protocole == Protocole.ISCLIENTREADY){
            System.out.println("Is client ready ?");
            Communication.writeProtocol(out,Protocole.CLIENTREADY);
        }else {
            throw new Exception("Patate" + protocole);
        }

        ArrayList<Scoot> scoots = new ArrayList<>();
        scoots.add(new Scoot(new Point(0, 0)));
        scoots.add(new Scoot(new Point(0, 0)));
        scoots.add(new Scoot(new Point(0, 0)));


        if(Communication.readProtocol(in) == Protocole.SERVERINSTATUSFIRSTROUND){
            System.out.println("Server in first round");
            Communication.writeJsonStream(out, scoots, Unit.class);
        }else {
            throw new Exception("Patate2");
        }

        // Wait ACK ?

        System.out.println("Client quit");
        out.close();
        socket.close();
    }
}

