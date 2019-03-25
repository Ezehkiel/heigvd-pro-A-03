package ch.heigvd.pro.a03.socketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


// RES Exemple
public class MultiThreadedServer implements Runnable{

    final static Logger LOG = Logger.getLogger(MultiThreadedServer.class.getName());
    static ArrayList<Socket> connectedClient;

    int port;

    public MultiThreadedServer(int port) {
        this.port = port;

    }

    public void run() {
        LOG.info("Starting the socket server on port : " +port);
        new Thread(new ReceptionistWorker()).start();
    }

    private class ReceptionistWorker implements Runnable {


        @Override
        public void run() {
            ServerSocket serverSocket;
            connectedClient = new ArrayList<>();

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
                        connectedClient.add(clientSocket);
                        matchmakingPlayer.add(clientSocket);
                    }

                    new Thread(new GameServer(matchmakingPlayer)).start();
                    matchmakingPlayer.clear();
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}


                    /*JsonReader reader = new JsonReader(in);
                    Gson gson = new GsonBuilder().create();


                    reader.beginArray();
                    while (reader.hasNext()) {
                        Person person = gson.fromJson(reader, Person.class);
                        System.out.println(person.toString());
                    }
                    reader.close();
                    in.close();
                    out.close();
                    clientSocket.close();*/