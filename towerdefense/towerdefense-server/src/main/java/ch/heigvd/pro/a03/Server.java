package ch.heigvd.pro.a03;

import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.*;
import static spark.Spark.*;

public class Server {

    public static void main(String[] args) {


        MultiThreadedServer multi = new MultiThreadedServer(4567);
        multi.serveClients();

        try{
            Thread.sleep(50);
        }catch(Exception e){}
        try{
            Client.main(new String[]{});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}