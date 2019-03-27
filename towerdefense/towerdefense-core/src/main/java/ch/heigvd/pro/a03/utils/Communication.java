package ch.heigvd.pro.a03.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import ch.heigvd.pro.a03.warentities.units.Scoot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


public class Communication {
    public  static ArrayList readJson(InputStreamReader in, Class classname) throws IOException {
        Gson gson = new GsonBuilder().create();
        JsonReader reader = new JsonReader(in);
        reader.beginArray();

        ArrayList recievedObject  = new ArrayList<>();

        while (reader.hasNext()) {
            recievedObject.add(gson.fromJson(reader,classname));
        }
        reader.close();
        in.close();
        return recievedObject;
    }
    public static void writeJsonStream(OutputStreamWriter out, ArrayList<Scoot> units) throws IOException {
        Gson json = new Gson();

        JsonWriter writer = new JsonWriter(out);
        writer.setIndent("  ");
        writer.beginArray();
        for (Scoot p : units) {
            json.toJson(p, Scoot.class, writer);
        }
        writer.endArray();
        writer.close();
    }
    public static void writeProtocol(OutputStreamWriter out,int protocol){
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
    public static int readProtocol(InputStreamReader in) throws IOException {
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
