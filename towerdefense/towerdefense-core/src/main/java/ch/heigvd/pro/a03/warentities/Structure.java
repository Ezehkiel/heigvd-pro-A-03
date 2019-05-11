package ch.heigvd.pro.a03.warentities;

import ch.heigvd.pro.a03.EventManager;
import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.algorithm.NearestTarget;
import ch.heigvd.pro.a03.event.simulation.AttackEvent;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;

public abstract class Structure extends WarEntity {

    private int attackTics = 0;

    public Structure(String name,Point position, int totalHealth, int defPoint,int attackCoolDown) {
        super(name,position,totalHealth,defPoint, attackCoolDown);
    }

    @Override
    public void update(int tickId, Map map) {

        attackTics++;

        if(attackTics == this.getAttackCoolDown()){
            Unit nearestUnit = NearestTarget.getNearestInRangeUnit(this, map);
            if(nearestUnit != null) {
                EventManager.getInstance().addEvent(new AttackEvent(tickId,getId(),nearestUnit.getId(),attack(nearestUnit)));
            }

            attackTics = 0;
        }
    }
}
