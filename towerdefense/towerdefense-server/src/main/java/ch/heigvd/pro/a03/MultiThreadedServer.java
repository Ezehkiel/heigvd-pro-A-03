package ch.heigvd.pro.a03;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;


// Fake objedt to transmit
class Person {
    private int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "nom :" + name ;
    }
}

// RES Exemple
public class MultiThreadedServer {

    final static Logger LOG = Logger.getLogger(MultiThreadedServer.class.getName());
    static ArrayList<Socket> allClientConnected;

    int port;

    public MultiThreadedServer(int port) {
        this.port = port;

    }

    public void serveClients() {
        LOG.info("Starting the Receptionist Worker on a new thread...");
        new Thread(new ReceptionistWorker()).start();
    }

    private class ReceptionistWorker implements Runnable {


        @Override
        public void run() {
            ServerSocket serverSocket;
            allClientConnected = new ArrayList<>();

            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return;
            }
            ArrayList<Socket> matchmakingPlayer = new ArrayList<>();

            while (true) {
                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
                try {
                    while (matchmakingPlayer.size() < 2){
                        Socket clientSocket = serverSocket.accept();
                        allClientConnected.add(clientSocket);
                        matchmakingPlayer.add(clientSocket);
                    }

                    new Thread(new GameServer(matchmakingPlayer)).start();
                    matchmakingPlayer.clear();
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        private class ServantWorker implements Runnable {

            Socket clientSocket;
            InputStreamReader in = null;
            OutputStreamWriter out = null;

            public ServantWorker(Socket clientSocket) {
                try {
                    this.clientSocket = clientSocket;

                    out = new OutputStreamWriter(clientSocket.getOutputStream()) {
                    };
                    out.flush();

                    in = new InputStreamReader (clientSocket.getInputStream());

                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void run(){
                try {
                    JsonReader reader = new JsonReader(in);
                    Gson gson = new GsonBuilder().create();


                    reader.beginArray();
                    while (reader.hasNext()) {
                        Person person = gson.fromJson(reader, Person.class);
                        System.out.println(person.toString());
                    }
                    reader.close();
                    in.close();
                    out.close();
                    clientSocket.close();

                } catch (IOException ex) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
    }
}
