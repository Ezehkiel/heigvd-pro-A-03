package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;

public class SlowerTurret extends Turret {

    public SlowerTurret(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int attackPoints, int range,int price) {
        super(name,position,totalHealth,defPoint,attackCoolDown,attackPoints,range,price);
    }


}
