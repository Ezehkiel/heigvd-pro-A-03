package ch.heigvd.pro.a03.warentities;


import java.awt.*;

abstract public class WarEntity {

    private Point position;//position that the entity will take at the grid 

    private int totalHealth;
    private int healthPoints;
    private int defensePoint;
    private int attackPoints;
    private int speed;
    private double range;
    private int price;

    public WarEntity(Point position, int totalHealth, int defensePoint) {
        this.position = position;
        this.totalHealth=totalHealth;
        this.healthPoints=totalHealth;
        this.defensePoint=defensePoint;
        this.attackPoints=0;
        this.speed=0;
        this.range=0;
        this.price=0;
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

        //distance between two points, circle formula.
        double distance = Math.sqrt(Math.pow((double) (target.getPosition().x - position.x), 2) +
                                    Math.pow((double) (target.getPosition().y - position.y), 2));

        if (distance >= range) {
            return true;
        }

        return false;
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

    public void setPrice(int price){
        this.price = price;
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
}
