package ch.heigvd.pro.a03;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private int money;
    private static int count=0;
    public final int ID;

    public Player(String name) {
        this.name = name;
        this.money = 3500;
        ID=count++;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public int getID() {
        return ID;
    }

    public void addMoney(int money){

        if(money>=0) {
            this.money += money;
        }
    }

    public void removeMoney(int money) {
        if (money<0) {
            if (this.money - money >= 0) {
                this.money -= money;
            } else {
                System.out.println("can't afford the entity");
            }

        }
    }

}
