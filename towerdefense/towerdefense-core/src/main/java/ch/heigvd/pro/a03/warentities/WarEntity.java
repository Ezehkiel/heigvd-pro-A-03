package ch.heigvd.pro.a03.warentities;


import ch.heigvd.pro.a03.Map;

import java.awt.*;

abstract public class WarEntity {

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

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public boolean isEntityDestroyed() {

        return (healthPoints == 0);
    }

    /**
     * @param target the Entity we desire check if its in range
     * @return true if its in range
     * @breif Uses the distance between this and target to determine if target is in range.
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
     * @param damageTaken damage inflicted by other Entity
     * @brief deals 200 damage.
     * 10 Defense: 181 damage
     * 100 Defense: 100 damage
     * 200 Defense: 66 damage
     * 500 Defense: 33 damage
     *
     * @return the damage inflicted or
     */
    public int dealDamage(int damageTaken) {

        int tmp = (damageTaken * 100) / (100 + defensePoint);
        int tmp2=0;

        if (healthPoints - tmp >= 0) {
            healthPoints -= tmp;
        } else {
            tmp2=healthPoints;
            healthPoints = 0;

        }

        return Math.min(tmp,tmp2);
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

    public abstract void update(int tickId, Map map);

    public int getAttackCoolDown() {
        return attackCoolDown;
    }
}
