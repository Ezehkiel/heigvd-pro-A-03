package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;

public class LaserGunTurret extends Turret {

    public LaserGunTurret(String name, Point position, int totalHealth, int defPoint, int attackCoolDown, int attackPoints, int range, int price) {
        super(name,position,totalHealth,defPoint,attackCoolDown,attackPoints,range,price);
    }

    public LaserGunTurret(Point position){
        this("LaserGun",position,500,1500,10,500,10,200);
    }

    @Override
    public String symbol() {
        if(isEntityDestroyed()) return "[X]";
        else return "LGT";
    }
}
