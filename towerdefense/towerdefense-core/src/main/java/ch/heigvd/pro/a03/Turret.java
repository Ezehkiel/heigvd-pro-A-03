package ch.heigvd.pro.a03;

abstract public class Turret extends WarEntity {

    private int range;
    private int attackPoints;

    abstract public Unite target();
}
