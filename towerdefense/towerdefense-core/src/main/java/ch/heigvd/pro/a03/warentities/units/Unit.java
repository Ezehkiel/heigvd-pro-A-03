package ch.heigvd.pro.a03.warentities.units;

import ch.heigvd.pro.a03.algorithm.Position;
import ch.heigvd.pro.a03.warentities.WarEntity;

import java.awt.*;

public class Unit extends WarEntity {


    public Unit(String name,Point position,int totalHealth, int defPoint, int speed, int attackPoints, int range,int price) {
        super(name,position,totalHealth,defPoint);
        super.setAttackPoints(attackPoints);
        super.setRange(range);
        super.setSpeed(speed);
        super.setPrice(price);

    }


    @Override
    public void update() {
        displacement();

    }

    public void displacement(){


    }


}
