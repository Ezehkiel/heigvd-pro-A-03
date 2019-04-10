package ch.heigvd.pro.a03.httpServer;


import ch.heigvd.pro.a03.ConnectionDB;
import ch.heigvd.pro.a03.users.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlRequest {

    private static Connection con = ConnectionDB.getInstance().getCon();

     static public User createUser(String username, String password){
        try {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO public.towerdefense_user(username, password) VALUES ( ?, ?) RETURNING id;");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return getUser(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public User getUser(long id){

        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("SELECT * FROM public.towerdefense_user WHERE id = ?;");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public User checkLogin(String username){
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("SELECT * FROM public.towerdefense_user WHERE username = ?;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }


        } catch (SQLException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        return null;
    }

     static public List<User> getAllUserDB(){
        List<User> users = new ArrayList<>();
         try {
             PreparedStatement stmt = null;
             stmt = con.prepareStatement("SELECT * FROM public.towerdefense_user;");
             ResultSet rs = stmt.executeQuery();

             while(rs.next()){
                 users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password")));
             }

         } catch (SQLException e) {
             e.printStackTrace();
         }

         return users;
     }

    static public User updateUser(String username, String password){

        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("UPDATE public.towerdefense_user SET password=? WHERE username = ? RETURNING id;");
            stmt.setString(1, password);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return getUser(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
