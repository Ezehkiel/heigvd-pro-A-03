package ch.heigvd.pro.a03.warentities.units;

import java.awt.*;

public class Tank extends Unit {

    public Tank(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int speed, int attackPoints, int range,int price) {
        super(name,position,totalHealth,defPoint,attackCoolDown,speed,attackPoints,range, price);

    }

    public Tank(Point position){
        super("Tank", position,500,500,10,15,500,4,500);
    }


}
