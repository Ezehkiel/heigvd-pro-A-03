package ch.heigvd.pro.a03.warentities;


import ch.heigvd.pro.a03.Map;

import java.awt.*;
import java.io.Serializable;

/***
 * Class representing the War Entities
 * @author Andres Moreno, Nicodeme Stalder, Nohan Budry
 */

abstract public class WarEntity implements Serializable {

    private Point position;//position that the entity will take at the grid
    protected int totalHealth;
    protected int healthPoints;
    protected int defensePoint;
    protected int attackPoints;
    protected int speed;
    protected int attackCoolDown;
    protected double range;
    protected int price;
    protected String name;
    private int id;

    /***
     * Constructor
     * @param name the name of the entity
     * @param position the position at the grid
     * @param totalHealth the total health
     * @param defensePoint the defense points
     * @param attackCoolDown the attack cool down
     */
    public WarEntity(String name, Point position, int totalHealth, int defensePoint, int attackCoolDown) {
        this.name = name;
        this.position = position;
        this.totalHealth = totalHealth;
        this.healthPoints = totalHealth;
        this.defensePoint = defensePoint;
        this.attackPoints = 0;
        this.speed = 0;
        this.range = 0;
        this.price = 0;
        this.attackCoolDown = attackCoolDown;
        this.id = -1;

    }

    /***
     *
     * @return the entity id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    public int getHealthPoint() {
        return healthPoints;
    }

    public double getRange() {
        return range;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getAttackCoolDown() {
        return attackCoolDown;
    }

    public boolean isEntityDestroyed() {
        return (healthPoints == 0);
    }

    /**
     * Uses the distance between this and target to determine if target is in range.
     * @param target the Entity we desire check if its in range
     * @return true if its in range
     *
     */
    public boolean isInRange(WarEntity target) {
        return distance(this, target) <= range;
    }

    /**
     * Calculate the straight distance between two WarEntities:
     * which is the x distance + the y distance between the WarEntities
     *
     * @param we1 a WarEntity
     * @param we2 another WarEntity
     * @return the straight distance between both specified WarEntities
     */
    public static int distance(WarEntity we1, WarEntity we2) {
        return Math.abs(we1.position.x - we2.position.x) + Math.abs(we1.position.y - we2.position.y);
    }

    /**
     * Example of use:
     * deals 200 damage.
     * 10 Defense: 181 damage
     * 100 Defense: 100 damage
     * 200 Defense: 66 damage
     * 500 Defense: 33 damage
     *
     * @param damageTaken damage inflicted by other Entity
     * @return the damage inflicted or
     */
    public int dealDamage(int damageTaken) {

        int tmp = (damageTaken * 100) / (100 + defensePoint);
        int damage = Math.min(tmp, healthPoints);

        healthPoints -= damage;

        return damage;
    }

    /**
     * kills the entity
     */
    public void kill() {
        healthPoints = 0;
    }

    /**
     * @param amount the amount of hp that will be restored
     */
    public void heal(int amount) {

        amount = Math.min(amount, totalHealth - healthPoints);
        healthPoints += amount;

    }


    /**
     *
     * @param target attack target
     * @return the damage inflicted
     */
    public int attack(WarEntity target) {

        int tmp=0;

        if (isInRange(target)) {
            tmp=target.dealDamage(attackPoints);
        }
        return tmp;
    }

    @Override
    public String toString() {
        return "WarEntity{" +
                "name=" + name +
                "position=" + position +
                ", totalHealth=" + totalHealth +
                ", healthPoint=" + healthPoints +
                ", defensePoint=" + defensePoint +
                ", attackPoints=" + attackPoints +
                ", range=" + range +
                '}';
    }

    //Should return a 3 characters string representing the WarEntity. Ex: " B ", "Sol" etc...
    abstract public String symbol();

    /***
     * Will update the entity champs
     *
     * @param tickId the current tick of the match
     * @param map the grid
     */
    public abstract void update(int tickId, Map map);

}
