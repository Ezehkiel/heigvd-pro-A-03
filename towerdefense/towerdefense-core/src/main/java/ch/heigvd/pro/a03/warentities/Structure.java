package ch.heigvd.pro.a03.warentities;

import ch.heigvd.pro.a03.EventManager;
import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.algorithm.NearestTarget;
import ch.heigvd.pro.a03.event.simulation.AttackEvent;
import ch.heigvd.pro.a03.event.simulation.DeathEvent;
import ch.heigvd.pro.a03.warentities.units.Unit;
import java.awt.*;


/***
 * class representing the structures of the game
 * @author  Andres Moreno, Nicodeme Stalder
 */
public abstract class Structure extends WarEntity {

    private int attackTics = 0;

    /***
     * Constructor
     * @param name the name of the structure
     * @param position the position of the structure at the gird
     * @param totalHealth the total health of the structure
     * @param defPoint the defense points of the structure
     * @param attackCoolDown the attack cool down
     */
    public Structure(String name,Point position, int totalHealth, int defPoint,int attackCoolDown) {
        super(name,position,totalHealth,defPoint, attackCoolDown);
    }

    @Override
    public void update(int tickId, Map map) {

        if (isEntityDestroyed()) {
            return;
        }

        attackTics++;

        if(attackTics == this.getAttackCoolDown()){
            Unit nearestUnit = NearestTarget.getNearestInRangeUnit(this, map);
            if(nearestUnit != null) {
                EventManager.getInstance().addEvent(new AttackEvent(tickId,getId(),nearestUnit.getId(),attack(nearestUnit),map.ID));
                if(nearestUnit.isEntityDestroyed()){
                    EventManager.getInstance().addEvent(new DeathEvent(tickId,nearestUnit.getId(),map.ID));
                }
            }

            attackTics = 0;
        }
    }
}
