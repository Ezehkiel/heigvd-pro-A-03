package ch.heigvd.pro.a03;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class Client {
    static final String serverName = "localhost";
    static final int serverPort = 4567;

    public static void main(String[] args) throws Exception {

        // On créer le socket
        Socket socket = new Socket(serverName, serverPort);

        // Créer l'output Stream
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
        out.flush();


        ArrayList<Person> personnes = new ArrayList<>();
        personnes.add(new Person(1,"Alice"));
        personnes.add(new Person(2,"Bob"));

        writeJsonStream(out, personnes);
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
