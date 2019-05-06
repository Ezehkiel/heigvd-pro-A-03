package ch.heigvd.pro.a03.socketServer;


import ch.heigvd.pro.a03.event.player.PlayerEvent;
import ch.heigvd.pro.a03.event.player.TurretEvent;
import ch.heigvd.pro.a03.event.player.*;

import javax.naming.ldap.SortKey;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.heigvd.pro.a03.event.player.TurretEventType.ADD;


// RES Exemple
public class SocketServer implements Runnable{

    final static Logger LOG = Logger.getLogger(SocketServer.class.getName());
    ArrayList<GameServer> gameServers;
    int port;



    public SocketServer(int port) {
        this.port = port;
        gameServers = new ArrayList<>();
    }

    public void run() {
        LOG.info("Starting the socket server on port : " +port);
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        while (true) {
            LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
            try {

                Socket clientSocket = serverSocket.accept();

                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                TurretEvent pe = new TurretEvent(1, ADD,new Point(1,3));

                oos.writeObject(pe);

                new Thread(new Worker(clientSocket)).start();
                System.out.println("Writed");

            } catch (IOException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}