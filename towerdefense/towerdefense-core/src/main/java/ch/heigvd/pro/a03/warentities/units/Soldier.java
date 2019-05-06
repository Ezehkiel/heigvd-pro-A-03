package ch.heigvd.pro.a03.warentities.units;

import java.awt.*;

public class Soldier extends Unit {

    public Soldier(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int speed, int attackPoints, int range, int price) {
        super(name,position,totalHealth,defPoint,attackCoolDown,speed,attackPoints,range, price);

    }
}
