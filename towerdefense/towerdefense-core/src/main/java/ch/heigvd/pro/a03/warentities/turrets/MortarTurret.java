package ch.heigvd.pro.a03.warentities.turrets;

import java.awt.*;

public class MortarTurret extends Turret {

    public MortarTurret(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int attackPoints, int range,int price) {
        super(name,position, totalHealth,defPoint,attackCoolDown,attackPoints,range,price);
    }

    public MortarTurret(Point position){
        this("Mortar",position,500,1500,10,500,10,200);
    }
}
