package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.Map;

import java.awt.*;

public class MachineGunTurret extends Turret {

    public MachineGunTurret(String name,Point position,int totalHealth, int defPoint, int attackPoints, int range,int price) {
        super(name,position,totalHealth,defPoint,attackPoints,range,price);
    }

}
