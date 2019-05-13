package ch.heigvd.pro.a03.warentities;

import org.json.JSONObject;

import java.awt.*;

public class Base extends Structure {

    private boolean endGame;

    public Base(String name,Point position, int totalHealth, int defPoint, int attackCoolDown) {
        super(name,position, totalHealth, defPoint,attackCoolDown);
        this.setAttackPoints(1000);
        endGame = false;
    }

    public Base(Point position){
        this("Base",position,15000,900, 100);
    }

    private Base(int id, Point position, int health) {
        this(position);
        setId(id);
        this.healthPoints = health;
    }


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
