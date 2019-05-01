package ch.heigvd.pro.a03;

//TODO: Add player class to core if needed.

/**
 * Represents a Player.
 */
public class Player {

    private int id;
    private String username;

    public Player(int id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * Gets the id of the player
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the username of the player
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Write the id and the username
     * @return id username
     */
    public String toString(){
        return this.id + " " + this.username;
    }
}
