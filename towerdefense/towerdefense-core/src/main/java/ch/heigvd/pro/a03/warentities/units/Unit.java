package ch.heigvd.pro.a03.warentities.units;

import ch.heigvd.pro.a03.utils.FloatPoint;
import ch.heigvd.pro.a03.warentities.WarEntity;

public class Unit extends WarEntity {
    public Unit(String name,FloatPoint position) {
        super(name);
        this.position = position;
    }

    private FloatPoint position;
}
