package ch.heigvd.pro.a03.httpServer;

import ch.heigvd.pro.a03.Server;
import ch.heigvd.pro.a03.users.User;
import java.sql.*;

 public class SqlRequest {

    Connection con = Server.getConDB().getCon();


    public boolean createUser(String username, String password){

        try {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO public.towerdefense_user(username, password) VALUES ( ?, ?);");
            stmt.setString(1, username);
            stmt.setString(2, password);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User getUser(long id){

        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("SELECT * FROM public.towerdefense_user WHERE id = ?;");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return new User(rs.getString("username"), rs.getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User checkLogin(String username){
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("SELECT * FROM public.towerdefense_user WHERE username = ?;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new User(rs.getString("username"), rs.getString("password"));
            }


        } catch (SQLException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        return null;
    }
}
