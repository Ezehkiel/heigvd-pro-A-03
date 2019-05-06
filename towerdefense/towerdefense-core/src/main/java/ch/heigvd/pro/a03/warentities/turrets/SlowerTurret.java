package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;

public class SlowerTurret extends Turret {

    public SlowerTurret(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int attackPoints, int range,int price) {
        super(name,position,totalHealth,defPoint,attackCoolDown,attackPoints,range,price);
    }

    /**
     * @breif for a certain time the speed of the Unit will be decreased by half.
     * @param troop the unit that will be slowered.
     */
    public void Slower(Unit troop){

        int tmp=troop.getSpeed();
        long endTime = System.currentTimeMillis() + 15000;

        while (System.currentTimeMillis() < endTime) {
            troop.setSpeed(troop.getSpeed() / 2);
        }
        troop.setSpeed(tmp);

    }


}
