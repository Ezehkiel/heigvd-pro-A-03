package ch.heigvd.pro.a03.warentities.units;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;


/***
 * Class representing a Soldier
 * @Author Andres Moreno
 */
public class Soldier extends Unit {

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
    public Soldier(String name, Point position, int totalHealth,
                   int defPoint, int attackCoolDown, int speed,
                   int attackPoints, int range, int price) {

        super(name, position, totalHealth,
                defPoint, attackCoolDown, speed,
                attackPoints, range, price, WarEntityType.UnitType.SOLIDER);

    }

    /***
     * Constructor
     * @param position the position at the gird
     */
    public Soldier(Point position){
        this("Soldier", position,200,200,10,
                5,100,3,200);
    }

    @Override
    public String symbol() {
        if(isEntityDestroyed()) return "X";
        else return "Sol";
    }

}
