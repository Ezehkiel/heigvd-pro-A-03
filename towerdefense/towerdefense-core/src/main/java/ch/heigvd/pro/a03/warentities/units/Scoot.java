package ch.heigvd.pro.a03.warentities.units;

import java.awt.*;

public class Scoot extends Unit {

    public Scoot(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int speed, int attackPoints, int range, int price) {
        super(name,position,totalHealth,defPoint,attackCoolDown,speed,attackPoints,range, price);

    }

    public Scoot(Point position){
        super("Soldier", position,180,150,7,3,80,2,75);
    }


}