package ch.heigvd.pro.a03.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.heigvd.pro.a03.actions.PlayerEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


public class Communication {
    public  static PlayerEvent readPlayerEventFromJson(InputStreamReader in) throws IOException {
        Gson gson = new GsonBuilder().create();
        JsonReader reader = new JsonReader(in);
        PlayerEvent pe =  gson.fromJson(reader,PlayerEvent.class);
        reader.close();
        in.close();
        return pe;
    }
    public static void writePlayerEventToJson(OutputStreamWriter out, PlayerEvent playerEvent) throws IOException {
        Gson json = new Gson();
        JsonWriter writer = new JsonWriter(out);
        writer.setIndent("  ");
        json.toJson(playerEvent, PlayerEvent.class, writer);
        writer.close();
    }
    public static void writeProtocol(OutputStreamWriter out,int protocol) throws IOException {
        Logger.getLogger(Communication.class.getName()).log(Level.INFO, null,"Write protocol "+ protocol);
        out.write(protocol);
        out.flush();
        Logger.getLogger(Communication.class.getName()).log(Level.INFO, null,"Write end of transmission : -1");
        out.write(-1);
        out.flush();
    }
    public static int readProtocol(InputStreamReader in) throws IOException {
        int receivedProtocole=-1;
        Logger.getLogger(Communication.class.getName()).log(Level.INFO, null,"Read protocol");
        int data = in.read();
        while(data != 65535){
            receivedProtocole = data;
            data = in.read();
        }
        return receivedProtocole;
    }
}
