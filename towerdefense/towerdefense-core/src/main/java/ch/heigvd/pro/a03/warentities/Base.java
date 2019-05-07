package ch.heigvd.pro.a03.warentities;

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



    public boolean isEntityDestroyed() {

        endGame = super.isEntityDestroyed();

        return endGame;

    }
}
