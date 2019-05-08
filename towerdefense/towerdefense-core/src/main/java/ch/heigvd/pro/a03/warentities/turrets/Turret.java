package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.Point;

public class Turret extends Structure {


    public Turret(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int attackPoints, int range, int price) {
        super(name,position,totalHealth,defPoint,attackCoolDown);
        super.setRange(range);
        super.setAttackPoints(attackPoints);
        super.setPrice(price);
    }



}
