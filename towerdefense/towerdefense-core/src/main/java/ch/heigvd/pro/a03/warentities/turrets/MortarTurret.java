package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;


/***
 * Class representing a Mortar Turret
 * @author  Andres Moreno, Nicodeme Stalder
 */
public class MortarTurret extends Turret {

    /***
     * Constructor
     * @param name the name of the turret
     * @param position the position at the grid
     * @param totalHealth total health points
     * @param defPoint defense points
     * @param attackCoolDown attack cool down
     * @param attackPoints attack points
     * @param range range of attack
     * @param price the cost of the turret
     */
    public MortarTurret(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int attackPoints,
                        int range,int price) {

        super(name, position, totalHealth,
                defPoint, attackCoolDown, attackPoints,
                range, price, WarEntityType.TurretType.MORTAR);
    }

    /***
     * Constructor
     * @param position the position at the grid
     */
    public MortarTurret(Point position){
        this("Mortar",position,500,500,10,
                200,5,200);
    }

    @Override
    public String symbol() {
        if(isEntityDestroyed()) return "[X]";
        else return "MrT";
    }
}
