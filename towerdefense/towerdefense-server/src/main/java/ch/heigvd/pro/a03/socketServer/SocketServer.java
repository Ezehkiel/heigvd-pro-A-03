package ch.heigvd.pro.a03.socketServer;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Main class for the socket sever, it has been implemented as a singleton
 * to make sur we have only one instance of the server.
 * It accept all client connexion an start a worker to delegate the treatment
 */
public class SocketServer implements Runnable{

    final static Logger LOG = Logger.getLogger(SocketServer.class.getName());

    ArrayList<GameServer> gameServers;
    int port;
    private static SocketServer instance = null;

    /**
     * Singleon's constructor
     * @return An instance of the SocketServer
     */
    public static SocketServer getInstance() {
        if(instance == null){
            instance = new SocketServer(4567);
        }
        return instance;

    }

    /**
     * The reel constructor but private called by the getInstance() function
     * @param port
     */
    private SocketServer(int port) {
        this.port = port;
        this.gameServers = new ArrayList<>();
    }

    /**
     * The fonction runned in the thread
     */
    public void run() {
        LOG.info("Starting the socket server on port : " +port);

        ServerSocket serverSocket;

        try {
            // Open the socket
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        while (true) {
            LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
            try {

                Socket clientSocket = serverSocket.accept();

                //Start the worker for each
                new Thread(new Worker(clientSocket,gameServers)).start();

            } catch (IOException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ArrayList<GameServer> getGameServers() {
        return gameServers;
    }
}
