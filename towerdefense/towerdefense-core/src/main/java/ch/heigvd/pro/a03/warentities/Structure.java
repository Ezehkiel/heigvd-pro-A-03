package ch.heigvd.pro.a03.warentities;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.algorithm.NearestTarget;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;

public abstract class Structure extends WarEntity {

    private int attackTics = 0;

    public Structure(String name,Point position, int totalHealth, int defPoint,int attackCoolDown) {
        super(name,position,totalHealth,defPoint, attackCoolDown);
    }

    @Override
    public void update(Map map) {
        //Do nothing during the cool down period
        if(++attackTics != attackCoolDown) return;
        attackTics = 0;

        //we'll attack the nearest Unit which is in range if their is any
        Unit nearestUnit = NearestTarget.getNearestInRangeUnit(this, map);
        if(nearestUnit != null) attack(nearestUnit);
    }

}
