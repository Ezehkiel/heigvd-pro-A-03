package ch.heigvd.pro.a03;

import java.sql.*;

public class ConnectionDB {

    public  Connection connecterBd() {
        try {
            // mise en place du driver
            Class.forName("org.postgresql.Driver");
            String host = "jdbc:postgresql://127.0.0.1:5432/towerdefense";
            String user = "toweruser";
            String psw = "OTE5M2NhYmVmNjI0ZjI2ZWM0NjRhMjVl";
            Connection con = DriverManager.getConnection(host, user, psw);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM towerdefense_user");
            while (resultSet.next()) {
                System.out.printf("%n %s %s %s", resultSet.getString("id"), resultSet.getString("username"), resultSet.getString("mail"), resultSet.getString("password"));
            }
            con.close();
            return con;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}