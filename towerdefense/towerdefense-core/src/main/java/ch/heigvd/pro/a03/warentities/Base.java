package ch.heigvd.pro.a03.warentities;

import java.awt.*;

public class Base extends Structure {

    private boolean endGame;

    public Base(String name,Point position, int totalHealth, int defPoint) {
        super(name,position, totalHealth, defPoint);
        endGame = false;
    }


    public boolean isEntityDestroyed() {

        endGame = super.isEntityDestroyed();

        return endGame;

    }
}
