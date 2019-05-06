package ch.heigvd.pro.a03.warentities;


import ch.heigvd.pro.a03.Map;

import java.awt.*;

abstract public class WarEntity {

    private Point position;//position that the entity will take at the grid 

    private int totalHealth;
    private int healthPoints;
    private int defensePoint;
    private int attackPoints;
    private int speed;
    private int attackCoolDown;
    private double range;
    private int price;
    private String name;

    private static int count=0;
    public final int ID;

    public WarEntity(String name ,Point position, int totalHealth, int defensePoint, int attackCoolDown) {
        this.name=name;
        this.position = position;
        this.totalHealth=totalHealth;
        this.healthPoints=totalHealth;
        this.defensePoint=defensePoint;
        this.attackPoints=0;
        this.speed=0;
        this.range=0;
        this.price=0;
        this.attackCoolDown =attackCoolDown;
        this.ID=count;
        count++;


    }

    public boolean isEntityDestroyed(){

        return (healthPoints==0);
    }

    /**
     * @breif Uses the distance formula between two points in order to determine if its in range.
     * @param target the Entity we desire check if its in range
     * @return true if its in range
     */
    private boolean isInRange(WarEntity target) {

        int distance = Math.abs(target.getPosition().x - position.x) +Math.abs(target.getPosition().y - position.y);

        return distance <= range;

    }

    /**
     * @brief
     * deals 200 damage.
     * 10 Defense: 181 damage
     * 100 Defense: 100 damage
     * 200 Defense: 66 damage
     * 500 Defense: 33 damage
     *
     * @param damageTaken damage inflicted by other Entity
     */
    public void dealDamage(int damageTaken) {

        healthPoints -= damageTaken*(100/(100+defensePoint));

    }

    /**
     *
     * @param amount the amount of hp that will be restored
     *
     */
    public void heal(int amount) {

        amount = Math.min(amount, totalHealth - healthPoints);
        healthPoints += amount;

    }

    public void attack(WarEntity target) {

        if (isInRange(target)) {
            target.dealDamage(attackPoints);
        }

    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getHealthPoint() {
        return healthPoints;
    }

    public int getDefensePoint() {
        return defensePoint;
    }

    public int getAttackPoints() {
        return attackPoints;
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

    public void setSpeed(int speed){
        this.speed=speed;
    }

    public int getSpeed(){
        return speed;
    }

    public void setPrice(int speed){
        this.price=price;
    }

    public int getPrice(){
        return price;
    }

    @Override
    public String toString() {
        return "WarEntity{" +
                "position=" + position +
                ", totalHealth=" + totalHealth +
                ", healthPoint=" + healthPoints +
                ", defensePoint=" + defensePoint +
                ", attackPoints=" + attackPoints +
                ", range=" + range +
                '}';
    }

    public abstract void update(Map map);

    public int getAttackCoolDown() {
        return attackCoolDown;
    }
}
