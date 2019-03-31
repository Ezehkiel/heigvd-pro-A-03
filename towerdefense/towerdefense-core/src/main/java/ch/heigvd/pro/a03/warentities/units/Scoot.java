package ch.heigvd.pro.a03.warentities.units;

import ch.heigvd.pro.a03.utils.FloatPoint;

public class Scoot extends Unit {

    public Scoot(String name) {
        super(name,new FloatPoint(1,1));
    }
    @Override
    public String toString() {
        return "nom :" + name ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}