package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.Map;

import java.awt.*;

public class MachineGunTurret extends Turret {

    public MachineGunTurret(String name,Point position,int totalHealth, int defPoint,int attackCoolDown, int attackPoints, int range,int price) {
        super(name,position,totalHealth,defPoint,attackCoolDown,attackPoints,range,price);
    }

    public MachineGunTurret(Point position){
        this("MachineGun",position,500,1500,10,500,10,200);
    }

    @Override
    public String symbol() {
        if(isEntityDestroyed()) return "[X]";
        else return "MGT";
    }
}
