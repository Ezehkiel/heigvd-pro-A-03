package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.warentities.Structure;

import java.awt.*;

public class Turret extends Structure {
    public Turret(Point position,int totalHealth, int defPoint, int attackPoints, int range) {
        super(position,totalHealth,defPoint);
        super.setRange(range);
        super.setAttackPoints(attackPoints);
    }
}
