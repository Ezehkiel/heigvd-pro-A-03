package ch.heigvd.pro.a03.warentities;

import java.awt.*;

public class Structure extends WarEntity {

    public Structure(String name, Point position) {
        super(name);
        this.position = position;
    }

    private Point position;
}
