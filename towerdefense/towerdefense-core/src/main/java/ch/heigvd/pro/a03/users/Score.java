package ch.heigvd.pro.a03.users;

/**
 * A simple object to modelise a scoreuse to simplifie the display on the client
 * @Author Remi Poulard
 */
public class Score {
    private int userId;
    private String username;
    private int nbPartieJoue;
    private int nbPartieGagne;

    /**
     * Constructor
     * @param userId the database id
     * @param username the username of the player
     * @param nbPartieJoue the new value of the number of game played
     * @param nbPartieGagne  the new value of the number of game winned
     */
    public Score(int userId, String username, int nbPartieJoue, int nbPartieGagne){
        this.userId = userId;
        this.username = username;
        this.nbPartieJoue = nbPartieJoue;
        this.nbPartieGagne = nbPartieGagne;
    }

    /**
     * Getter for the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the nbGamePlayed
     * @return the number of played game
     */
    public int getNbPartieJoue() {
        return nbPartieJoue;
    }

    /**
     * Getter for the number of winned game
     * @return the number of winnecd game
     */
    public int getNbPartieGagne() {
        return nbPartieGagne;
    }
}
