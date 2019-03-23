package ch.heigvd.pro.a03;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnexionBd {

    public static Connection connecterBd(){
        try{
            // mise en place du driver
            Class.forName("com.mysql.jdbc.Driver");
            //TODO: creer une base de données puis insérer une le chemin vers la base
            String host = "";
            String user = "";
            String psw = "";
            Connection con = DriverManager.getConnection(host, user, psw);
            return con;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
