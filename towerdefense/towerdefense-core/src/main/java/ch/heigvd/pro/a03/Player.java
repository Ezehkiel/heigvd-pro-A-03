package ch.heigvd.pro.a03;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private int money;
    public final int ID;

    private Player(int ID, String name, int money) {
        this.ID = ID;
        this.name = name;
        this.money = money;
    }

    public Player(int id, String name) {
        this(id, name, 3500);
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int money){

        if(money>=0) {
            this.money += money;
        }
    }

    public void removeMoney(int money) {
        if (money>=0) {
            if (this.money - money >= 0) {
                this.money -= money;
            } else {
                System.out.println("can't afford the entity");
            }

        }
    }
    public static Player getPlayer(ObjectInputStream in) {
        Player player = null;
        try {
            player = (Player) in.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }

        System.out.println(player);

        return player;
    }
    public static void sendPlayer(Player player, ObjectOutputStream out) {
        try {
            out.writeObject(player);
            out.flush();
//            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toJson() {

        JSONObject player = new JSONObject();
        player.put("id", ID);
        player.put("name", name);
        player.put("money", money);

        return player.toString();
    }

    public static Player fromJson(String json) {

        JSONObject object = new JSONObject(json);
        System.out.println(object.toString());
        return new Player(object.getInt("id"), object.getString("name"), object.getInt("money"));
    }
}
