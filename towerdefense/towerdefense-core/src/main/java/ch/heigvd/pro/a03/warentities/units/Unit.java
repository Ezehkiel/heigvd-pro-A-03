package ch.heigvd.pro.a03.warentities.units;

import ch.heigvd.pro.a03.algorithm.Position;
import ch.heigvd.pro.a03.warentities.WarEntity;

import java.awt.*;

public class Unit extends WarEntity {


    public Unit(Point position,int totalHealth, int defPoint, int speed, int attackPoints, int range) {
        super(position,totalHealth,defPoint);
        super.setAttackPoints(attackPoints);
        super.setRange(range);
        super.setSpeed(speed);

    }


}
