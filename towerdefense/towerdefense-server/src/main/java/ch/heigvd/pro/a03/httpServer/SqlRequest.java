package ch.heigvd.pro.a03.httpServer;


import ch.heigvd.pro.a03.ConnectionDB;
import ch.heigvd.pro.a03.users.Score;
import ch.heigvd.pro.a03.users.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlRequest {

    private static Connection con = ConnectionDB.getInstance().getCon();

     static public User createUserDB(String username, String password){
        try {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO public.towerdefense_user(username, password, lastLogin) VALUES ( ?, ?, now()) RETURNING id;");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return getUserDBWithId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public User getUserDBWithId(long id){

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

    static public User getUserDBWithUsername(String username){

        try {
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT * FROM public.towerdefense_user WHERE username = ?;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                        rs.getInt("nbPartieJoue"), rs.getInt("nbPartieGagne"), rs.getDate("lastLogin"));
            }


        } catch (SQLException e) {
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
                 users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password")
                 , rs.getInt("nbPartieJoue"), rs.getInt("nbPartieGagne"), rs.getDate("lastLogin")));
             }

         } catch (SQLException e) {
             e.printStackTrace();
         }

         return users;
     }

    static public User updateUserDB(String username, String password){

        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("UPDATE public.towerdefense_user SET password=? WHERE username = ? RETURNING id;");
            stmt.setString(1, password);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return getUserDBWithId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public void setLastLoginDB(long id){

        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("UPDATE public.towerdefense_user SET lastlogin = now() WHERE id = ?;");
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public List<Score> getAllScoreDB(){
        List<Score> scores = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("SELECT id, nbPartieJoue, nbPartieGagne FROM public.towerdefense_user;");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                scores.add(new Score(rs.getInt("id"),rs.getInt("nbPartieJoue"), rs.getInt("nbPartieGagne")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }

    static public boolean incrementPlayedGameUserDB(long id) {
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("UPDATE public.towerdefense_user SET nbpartiejoue= nbpartiejoue +1 WHERE id=?;");
            stmt.setLong(1, id);
            stmt.executeQuery();

            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    static public boolean incrementWinGameUserDB(long id) {
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("UPDATE public.towerdefense_user SET nbpartiegagne= nbpartiegagne + 1 WHERE id=?;");
            stmt.setLong(1, id);
            stmt.executeQuery();

            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    static public Score getUserScoreDB(long id){
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("SELECT id, nbPartieJoue, nbPartieGagne FROM public.towerdefense_user WHERE id=?;");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                return new Score(rs.getInt("id"),rs.getInt("nbPartieJoue"), rs.getInt("nbPartieGagne"));
            }

        } catch (SQLException e) {
            return null;
        }

        return null;
    }
}
