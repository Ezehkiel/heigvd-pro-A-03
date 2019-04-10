package ch.heigvd.pro.a03;

import java.sql.*;

public class ConnectionDB {

    private static ConnectionDB instance;

    public static ConnectionDB getInstance() {
        if(instance == null){
            instance = new ConnectionDB();
        }
        return instance;
    }

    private Connection con;

    public Connection getCon() {
        return con;
    }

    private ConnectionDB() {
        try {
            // mise en place du driver
            Class.forName("org.postgresql.Driver");
            String host = "jdbc:postgresql://127.0.0.1:5432/towerdefense";
            String user = "toweruser";
            String psw = "OTE5M2NhYmVmNjI0ZjI2ZWM0NjRhMjVl";
            this.con = DriverManager.getConnection(host, user, psw);

        } catch (Exception e) {
            e.printStackTrace();
            this.con = null;
        }

    }
}