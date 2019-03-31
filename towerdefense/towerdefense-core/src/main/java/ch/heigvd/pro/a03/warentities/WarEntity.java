package ch.heigvd.pro.a03.warentities;


import java.awt.*;

abstract public class WarEntity {
    public String name;
    private int hp;
    private int defensePoint;
    private int range;
    private int attackPoints;

    public WarEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "WarEntity{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", defensePoint=" + defensePoint +
                ", range=" + range +
                ", attackPoints=" + attackPoints +
                '}';
    }
}
