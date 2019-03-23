package ch.heigvd.pro.a03;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import ch.heigvd.pro.a03.state.ServerState;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class FakeClient {
    static final String serverName = "localhost";
    static final int serverPort = 4567;

    public static void main(String[] args) throws Exception {
        System.out.println("Client start");

        // On créer le socket
        Socket socket = new Socket(serverName, serverPort);

        // Créer l'output Stream
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
        out.flush();

        InputStreamReader in = new InputStreamReader(socket.getInputStream());

        System.out.println("Client wait for the server");
        int data = in.read();


        while(data != 65535){
            System.out.println(data);
            data = in.read();
        }
        System.out.println("Client has recieved protocol from the server");

        try{
            System.out.println("Client write protocol to server");
            out.write(Protocole.CLIENTREADY);
            out.flush();
            System.out.println("client write end of transmission : -1");
            out.write(-1);
            out.flush();
            System.out.println("Client writed");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }



        /*ArrayList<Person> personnes = new ArrayList<>();
        personnes.add(new Person(1,"Alice"));
        personnes.add(new Person(2,"Bob"));

        writeJsonStream(out, personnes);*/
        System.out.println("Client quit");
        out.close();
        socket.close();
    }

    static void writeJsonStream(OutputStreamWriter out, ArrayList<Person> personList) throws IOException {
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
    }



}
