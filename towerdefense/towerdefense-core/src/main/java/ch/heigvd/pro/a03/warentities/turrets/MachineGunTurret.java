package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;

/***
 * Class representing a Machine-Gun Turret
 * @author  Andres Moreno, Nicodeme Stalder
 */
public class MachineGunTurret extends Turret {

    /***
     * Constructor
     * @param name the name of the turret
     * @param position the position at the grid
     * @param totalHealth total health points
     * @param defPoint defense points
     * @param attackCoolDown attack cool down
     * @param attackPoints attack points
     * @param range
     * @param price the cost of the turret
     */
    public MachineGunTurret(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int attackPoints, int range,int price) {
        super(name, position, totalHealth,
                defPoint, attackCoolDown, attackPoints,
                range, price, WarEntityType.TurretType.MACHINE_GUN);
    }

    public MachineGunTurret(Point position){
        this("MachineGun",position,500,1500,10,200,10,200);
    }

    @Override
    public String symbol() {
        if(isEntityDestroyed()) return "[X]";
        else return "MGT";
    }
}
