package ch.heigvd.pro.a03.users;

import java.util.Date;
import java.util.Objects;

/**
 * Class for un user (Player) with his personal informations
 * @Author Remi Poulard
 */
public class User {

    private String username;
    private String password;
    private int id;
    private String token;
    private int nbPartieJoue;
    private int nbPartieGagne;
    private Date lastLogin;

    /**
     * Constructor
     * @param id the database id
     * @param userName username
     * @param motDePasse password
     */
    public User(int id, String userName,String motDePasse){
        this.id = id;
        this.username = userName;
        this.password = motDePasse;
    }

    /**
     * Constructor
     * @param id
     * @param userName
     * @param motDePasse
     * @param nbPartieJoue
     * @param nbPartieGagne
     * @param lastLogin
     */
    public User (int id, String userName,String motDePasse, int nbPartieJoue, int nbPartieGagne, Date lastLogin){
        this(id, userName, motDePasse);
        this.nbPartieJoue = nbPartieJoue;
        this.nbPartieGagne = nbPartieGagne;
        this.lastLogin = lastLogin;
    }

    /**
     * @return Object parsed to string
     */
    @Override
    public String toString(){
        return this.id + " " + this.username ;
    }

    /**
     * Check if two users are equals
     * A user is equals to an another if the password and the username match
     * @param o the user to compare
     * @return true if the users are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.username, user.getUsername()) &&
                Objects.equals(this.password, user.password);
    }

    /**
     * Getter for the id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the token
     * @param token the JWT token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter for the number of game winned
     * @return the number of game
     */
    public int getGetNbPartieGagne() {
        return nbPartieGagne;
    }

    /**
     * Getter for the number of game played
     * @return
     */
    public int getNbPartieJoue() {
        return nbPartieJoue;
    }

}
