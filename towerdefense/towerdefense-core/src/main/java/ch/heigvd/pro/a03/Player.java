package ch.heigvd.pro.a03;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Information about a player used inside the game
 * @Author Andres Moreno
 */
public class Player implements Serializable {

    private String name;
    private int money;
    public final int ID;

    /**
     * Constructor
     * @param ID in game id
     * @param name the username
     * @param money the current amount of money
     */
    private Player(int ID, String name, int money) {
        this.ID = ID;
        this.name = name;
        this.money = money;
    }

    /**
     * Constructor
     * @param id in game id
     * @param name the player's username
     */
    public Player(int id, String name) {
        this(id, name, 3500);
    }

    /**
     * Getter for the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the money
     * @return the money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Function that add an amount of monex to the player
     * @param money
     */
    public void addMoney(int money){

        if(money>=0) {
            this.money += money;
        }
    }

    /**
     * Function that remove an amount of money
     * @param money the amount to remove
     */
    public void removeMoney(int money) {
        if (money>=0) {
            if (this.money - money >= 0) {
                this.money -= money;
            } else {
                System.out.println("can't afford the entity");
            }

        }
    }

    /**
     * Getter for the player throug the ObjectOutputStream
     * @param in Object input stream
     * @return the player
     */
    public static Player getPlayer(ObjectInputStream in) {
        Player player = null;
        try {
            player = (Player) in.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(player);

        return player;
    }

    /**
     * Parse the player to json as a string
     * @return the json
     */
    public String toJson() {

        JSONObject player = new JSONObject();
        player.put("id", ID);
        player.put("name", name);
        player.put("money", money);

        return player.toString();
    }

    /**
     * Creat a player from json
     * @param json the source json
     * @return the player created
     */
    public static Player fromJson(String json) {

        JSONObject object = new JSONObject(json);
        System.out.println(object.toString());
        return new Player(object.getInt("id"), object.getString("name"), object.getInt("money"));
    }
}
