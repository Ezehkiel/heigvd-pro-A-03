package ch.heigvd.pro.a03.httpServer;

import ch.heigvd.pro.a03.users.Score;
import ch.heigvd.pro.a03.users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contain all requests for the database
 *
 * @author Remi Poulard
 */
public class SqlRequest {

    private final static Logger LOG = Logger.getLogger(HttpServer.class.getName());
    private static Connection con = ConnectionDB.getInstance().getConnection();

    /**
     * In this method we create a new user and we return it
     *
     * @param username the username of the user
     * @param password the password of the user (hashed)
     * @return the user newly created
     */
    static public User createUserDB(String username, String password) {
        try {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO" +
                    " public.towerdefense_user(username, password, lastLogin)" +
                    " VALUES ( ?, ?, now()) RETURNING id;");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getUserDBWithId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error with the creation of a new user " +
                    "in the database");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method select all info about a user with his id
     *
     * @param id the id of the user that we want to find
     * @return a new user
     */
    static public User getUserDBWithId(long id) {

        try {
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT * FROM " +
                    "public.towerdefense_user WHERE id = ?;");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"));
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error to get a user in the DB");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method select all info about a user with his username
     *
     * @param username the username of the user that we want to find
     * @return a new user
     */
    static public User getUserDBWithUsername(String username) {

        try {

            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT * FROM " +
                    "public.towerdefense_user WHERE username = ?;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new User(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getInt("nbPartieJoue"),
                        rs.getInt("nbPartieGagne"), rs.getDate("lastLogin"));
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error to get a user in the DB");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * In this method we want to get all the user that are in the database
     *
     * @return a list with all the users
     */
    static public List<User> getAllUserDB() {

        try {
            List<User> users = new ArrayList<>();
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT * FROM " +
                    "public.towerdefense_user;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getInt("nbPartieJoue"),
                        rs.getInt("nbPartieGagne"), rs.getDate("lastLogin")));
            }

            return users;

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error to get all the users in the DB");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @param username the username of the user to update
     * @param password the new password of the user
     * @return the user updated
     */
    static public User updateUserDB(String username, String password) {

        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("UPDATE public.towerdefense_user SET" +
                    " password=? WHERE username = ? RETURNING id;");
            stmt.setString(1, password);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getUserDBWithId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method update the lastLogin timestamp of a user
     *
     * @param id of the user that we want to update the timestamp
     */
    static public void setLastLoginDB(long id) {

        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("UPDATE public.towerdefense_user SET" +
                    " lastLogin=now() WHERE id = ?;");
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error with the update of lastLogin");
            e.printStackTrace();
        }
    }

    /**
     * This method give all the scores that are in the database
     *
     * @return a list with all scores
     */
    static public List<Score> getAllScoreDB() {
        List<Score> scores = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("SELECT id, username, nbPartieJoue," +
                    " nbPartieGagne FROM public.towerdefense_user;");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                scores.add(new Score(rs.getInt("id"), rs.getString("username"),
                        rs.getInt("nbPartieJoue"), rs.getInt("nbPartieGagne")));
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error to get all scores in the DB");
            e.printStackTrace();
        }

        return scores;
    }

    /**
     * This method increment the counter of played game of a user
     *
     * @param id the id of the user that played a game
     * @return true if the update goes well, false otherwise
     */
    static public boolean incrementPlayedGameUserDB(long id) {
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("UPDATE public.towerdefense_user SET" +
                    " nbpartiejoue = nbpartiejoue + 1 WHERE id = ?;");
            stmt.setLong(1, id);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error to increment the playedGame counter");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method increment the counter of wined game of a user
     *
     * @param id the id of the user that played a game
     * @return true if the update goes well, false otherwise
     */
    static public boolean incrementWinGameUserDB(long id) {
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("UPDATE public.towerdefense_user SET" +
                    " nbpartiegagne= nbpartiegagne + 1 WHERE id=?;");
            stmt.setLong(1, id);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error to increment the win game counter");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method return the score for a particular user
     *
     * @param id of the user that we want to have score
     * @return score of the user
     */
    static public Score getUserScoreDB(long id) {
        try {
            PreparedStatement stmt = null;
            stmt = con.prepareStatement("SELECT id, username, nbPartieJoue, " +
                    "nbPartieGagne FROM public.towerdefense_user WHERE id=?;");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return new Score(rs.getInt("id"),rs.getString("username"),
                        rs.getInt("nbPartieJoue"), rs.getInt("nbPartieGagne"));
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error to get the score for a user");
            e.printStackTrace();
            return null;
        }

        return null;
    }
}
