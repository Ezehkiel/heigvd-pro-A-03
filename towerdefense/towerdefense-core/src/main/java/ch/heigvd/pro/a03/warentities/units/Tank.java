package ch.heigvd.pro.a03.warentities.units;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;

/***
 * Class representing a Tank
 * @Author Andres Moreno
 */
public class Tank extends Unit {

    /***
     * Constructor
     * @param name the unit name
     * @param position the position at the grid
     * @param totalHealth total health points
     * @param defPoint defense points
     * @param attackCoolDown attack cool down
     * @param attackPoints attack points
     * @param range
     * @param price the cost in order to buy it
     */
    public Tank(String name, Point position, int totalHealth,
                int defPoint, int attackCoolDown, int speed,
                int attackPoints, int range, int price) {

        super(name, position, totalHealth,
                defPoint, attackCoolDown, speed,
                attackPoints, range, price, WarEntityType.UnitType.TANK);

    }

    /***
     * Constructor
     * @param position the position at the grid
     */
    public Tank(Point position){
        this("Tank", position,500,500,10,15,
                500,4,500);
    }

    @Override
    public String symbol() {
        if(isEntityDestroyed()) return " X ";
        else return "Tnk";
    }

}
