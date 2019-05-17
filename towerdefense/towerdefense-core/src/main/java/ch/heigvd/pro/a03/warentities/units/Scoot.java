package ch.heigvd.pro.a03.warentities.units;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;

public class Scoot extends Unit {

    public Scoot(String name, Point position, int totalHealth,
                 int defPoint, int attackCoolDown, int speed,
                 int attackPoints, int range, int price) {

        super(name, position, totalHealth,
                defPoint, attackCoolDown, speed,
                attackPoints, range, price, WarEntityType.UnitType.SCOOT);

    }

    public Scoot(Point position){
        this("Soldier", position,180,150,7,3,80,2,75);
    }

    @Override
    public String symbol() {
        if(isEntityDestroyed()) return " X ";
        else return "Sct";
    }

}