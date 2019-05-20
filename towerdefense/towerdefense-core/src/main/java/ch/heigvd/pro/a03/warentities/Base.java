package ch.heigvd.pro.a03.warentities;

import org.json.JSONObject;

import java.awt.*;

/***
 * class representing the base
 * @author Andres Moreno, Nicodeme Stalder
 */
public class Base extends Structure {

    private boolean endGame;

    /***
     * Constructor
     * @param name the name of the base
     * @param position the position at the grid
     * @param totalHealth the total health
     * @param defPoint the defense points
     * @param attackCoolDown the attack cool down
     */
    public Base(String name,Point position, int totalHealth, int defPoint, int attackCoolDown) {
        super(name,position, totalHealth, defPoint,attackCoolDown);
        this.setAttackPoints(1000);
        this.setRange(5);
        endGame = false;
    }

    /***
     * Constructor
     * @param position the base position
     */
    public Base(Point position){
        this("Base",position,15000,900, 100);
    }

    /***
     * private constructor
     * @param id the base id
     * @param position the position in the grid
     * @param health the health points
     */
    private Base(int id, Point position, int health) {
        this(position);
        setId(id);
        this.healthPoints = health;
    }

    /***
     *
     * @return true if the base is destroyed
     */
    public boolean isEntityDestroyed() {

        endGame = super.isEntityDestroyed();

        return endGame;
    }

    @Override
    public String symbol() {
        if(isEntityDestroyed()) return "[X]";
        else return " B ";
    }

    public static Base fromJson(String json) {
        JSONObject base = new JSONObject(json);
        JSONObject position = base.getJSONObject("position");

        return new Base(
                base.getInt("id"),
                new Point(position.getInt("x"), position.getInt("y")),
                base.getInt("health")
        );
    }
}
