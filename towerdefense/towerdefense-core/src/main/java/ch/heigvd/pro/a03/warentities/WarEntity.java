package ch.heigvd.pro.a03.warentities;


import java.awt.*;

abstract public class WarEntity {

    private Point position;

    private int maxHealth = 20;
    private int healthPoint = 20;
    private int defensePoint = 2;
    private int attackPoints = 6;
    private int range = 1;

    public WarEntity(Point position) {
        this.position = position;
    }

    public int dealDamage(int damage) {

        damage = Math.min(damage - defensePoint, healthPoint);
        healthPoint -= damage;

        return damage;
    }

    public int heal(int amount) {

        amount = Math.min(amount, maxHealth - healthPoint);
        healthPoint += amount;

        return amount;
    }

    public int attack(WarEntity target) {

        if (isInRange(target)) {
            return target.dealDamage(attackPoints);
        }

        return -1;
    }

    public boolean isInRange(WarEntity target) {

        int distance = Math.abs(target.getPosition().x - position.x) +
                Math.abs(target.position.y - position.y);

        return distance <= range;
    }

    public Point getPosition() {
        return position;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getDefensePoint() {
        return defensePoint;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getRange() {
        return range;
    }

    @Override
    public String toString() {
        return "WarEntity{" +
                "position=" + position +
                ", maxHealth=" + maxHealth +
                ", healthPoint=" + healthPoint +
                ", defensePoint=" + defensePoint +
                ", attackPoints=" + attackPoints +
                ", range=" + range +
                '}';
    }
}
