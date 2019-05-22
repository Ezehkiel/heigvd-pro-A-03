package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.Point;

/***
 * Class representing the turrets of the game
 * @author  Andres Moreno, Nicodeme Stalder
 */
public abstract class Turret extends Structure {

    public final WarEntityType.TurretType TYPE;

    /***
     *
     * @param name the turret name
     * @param position the position at the grid
     * @param totalHealth total health points
     * @param defPoint defense points
     * @param attackCoolDown attack cool down
     * @param attackPoints attack points
     * @param range range of attack
     * @param price the cost in order to buy it
     * @param type the turret type
     */
    public Turret(String name, Point position, int totalHealth,
                  int defPoint, int attackCoolDown, int attackPoints,
                  int range, int price, WarEntityType.TurretType type) {

        super(name,position,totalHealth,defPoint,attackCoolDown);
        super.setRange(range);
        super.setAttackPoints(attackPoints);
        super.setPrice(price);

        TYPE = type;
    }



}
