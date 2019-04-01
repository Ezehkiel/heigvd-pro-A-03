package ch.heigvd.pro.a03;


import ch.heigvd.pro.a03.actions.PlayerEvent;
import ch.heigvd.pro.a03.actions.UnitAction;
import ch.heigvd.pro.a03.utils.Communication;
import ch.heigvd.pro.a03.utils.Protocole;

import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import static ch.heigvd.pro.a03.actions.UnitActionType.*;
import static ch.heigvd.pro.a03.utils.Communication.writePlayerEventToJson;
import static ch.heigvd.pro.a03.utils.Protocole.YOURAREPLAYERONE;
import static ch.heigvd.pro.a03.utils.Communication.readProtocol;
import static ch.heigvd.pro.a03.warentities.units.UnitType.SCOOT;


public class FakeClient {
    static final String serverName = "localhost";
    static final int serverPort = 4567;
    static OutputStreamWriter out;
    static InputStreamReader in;

    static boolean isPlayerOne;

    public static void main (String ... args) throws Exception {
        System.out.println("Client start");

        // On créer le socket
        Socket socket = new Socket(serverName, serverPort);

        // Créer l'output Stream
        out = new OutputStreamWriter(socket.getOutputStream());
        out.flush();

        in = new InputStreamReader(socket.getInputStream());

        Communication.writeProtocol(out, Protocole.CLIENTWANTPLAYMULTI);

        System.out.println("Client wait server validation");
        int protocole = readProtocol(in);


        if(protocole == Protocole.ISCLIENTREADY){
            System.out.println("Is client ready ?");
            Communication.writeProtocol(out,Protocole.CLIENTREADY);
        }else {
            throw new Exception("Patate" + protocole);
        }

        isPlayerOne = (readProtocol(in) == YOURAREPLAYERONE);

        ArrayList unitActions = new ArrayList<>();
        ArrayList turretActions = new ArrayList<>();

        unitActions.add(new UnitAction(SEND,10, SCOOT));

        PlayerEvent pe = new PlayerEvent(unitActions);

        System.out.println("Am'I player One ? " + isPlayerOne);


        if(readProtocol(in) == Protocole.SERVERINSTATUSINITIALISATION){
            writePlayerEventToJson(out, pe);
        }else {
            throw new Exception("Patate2");
        }

        // Wait ACK ?

        System.out.println("Client quit");
        out.close();
        socket.close();
    }
}

