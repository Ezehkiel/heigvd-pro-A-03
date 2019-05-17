package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.httpServer.HttpServer;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class make the connection with the database.
 *
 * @author
 */
public class ConnectionDB {

    final static Logger LOG = Logger.getLogger(HttpServer.class.getName());
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
            String hostDB = "jdbc:postgresql://127.0.0.1:5432/towerdefense";
            String userDB = "toweruser";
            String passwordDB = "OTE5M2NhYmVmNjI0ZjI2ZWM0NjRhMjVl";
            this.connection = DriverManager.getConnection(hostDB, userDB, passwordDB);

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