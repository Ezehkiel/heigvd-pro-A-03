package ch.heigvd.pro.a03.warentities;

import java.awt.*;

public class Base extends Structure {

    private boolean endGame;

    public Base(Point position, int totalHealth, int defPoint) {
        super(position, totalHealth, defPoint);
        endGame = false;
    }


    public boolean isEntityDestroyed() {

        endGame = super.isEntityDestroyed();

        return endGame;

    }
}
