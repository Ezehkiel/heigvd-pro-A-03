package ch.heigvd.pro.a03.httpServer;

import ch.heigvd.pro.a03.Server;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class make the connection with the database.
 *
 * @author Remi Poulard
 */
public class ConnectionDB {

    private final static Logger LOG = Logger.getLogger(HttpServer.class.getName());
    private static ConnectionDB instance;
    private Connection connection;

    /**
     * It's a singleton. Get the instance of the
     * class
     *
     * @return the instance of the class
     */
    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    /**
     * We create the connection with the database
     */
    private ConnectionDB() {
        try {
            // Mise en place du driver
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://" + Server.getDatabaseAddress() +
                    ":" + Server.getDatabasePort() + "/" + Server.getDatabaseName();
            this.connection = DriverManager.getConnection(url, Server.getDatabaseUser(), Server.getDatabasePassword());

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error with the connection to the database");
            e.printStackTrace();
            this.connection = null;
        }

    }

    /**
     * get the connection
     *
     * @return the connection with the database
     */
    public Connection getConnection() {
        return connection;
    }
}