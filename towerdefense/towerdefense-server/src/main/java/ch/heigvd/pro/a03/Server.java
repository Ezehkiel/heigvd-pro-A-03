package ch.heigvd.pro.a03;


import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.*;
import com.google.gson.Gson;

import static spark.Spark.*;

public class Server {


    public Server() {
        Gson gson = new Gson();

        get("/hello/:name", (request, response) -> HelloWorld.greet(request.params(":name")), gson::toJson);
    }

    public static void main(String[] args) {
        
        new Server();

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