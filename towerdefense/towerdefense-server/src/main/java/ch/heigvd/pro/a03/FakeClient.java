package ch.heigvd.pro.a03;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;
/*import java.util.ArrayList;

import ServerState;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;*/

public class FakeClient {
    static final String serverName = "localhost";
    static final int serverPort = 4567;
    static OutputStreamWriter out;
    static InputStreamReader in;

    public static void main(String[] args) throws Exception {
        System.out.println("Client start");

        // On créer le socket
        Socket socket = new Socket(serverName, serverPort);

        // Créer l'output Stream
        out = new OutputStreamWriter(socket.getOutputStream());
        out.flush();

        in = new InputStreamReader(socket.getInputStream());

        writeProtocol(Protocole.CLIENTWANTPLAYMULTI);

        System.out.println("Client wait server validation");
        int protocole = readProtocol();


        if(protocole == Protocole.ISCLIENTREADY){
            System.out.println("Is client ready ?");
            writeProtocol(Protocole.CLIENTREADY);
        }else {
            throw new Exception("Patate" + protocole);
        }

        // Wait ACK ?


            /*ArrayList<Person> personnes = new ArrayList<>();
            personnes.add(new Person(1,"Alice"));
            personnes.add(new Person(2,"Bob"));

            writeJsonStream(out, personnes);*/
        System.out.println("Client quit");
        out.close();
        socket.close();
    }

    /*static void writeJsonStream(OutputStreamWriter out, ArrayList<Person> personList) throws IOException {
        Gson json = new Gson();

        JsonWriter writer = new JsonWriter(out);
        writer.setIndent("  ");
        writer.beginArray();
        for (Person p : personList) {
            System.out.println(p);
            json.toJson(p, Person.class, writer);
        }
        writer.endArray();
        writer.close();
    }*/


    static void writeProtocol(int protocol){
        try{
            System.out.println("Client write protocol to server "+protocol);
            out.write(protocol);
            out.flush();
            System.out.println("client write end of transmission : -1");
            out.write(-1);
            out.flush();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    static int readProtocol() throws IOException {
        int receivedProtocole=-1;
        System.out.println("Client wait for protocol");
        int data = in.read();
        while(data != 65535){
            receivedProtocole = data;
            data = in.read();
        }
        return receivedProtocole;
    }
}

