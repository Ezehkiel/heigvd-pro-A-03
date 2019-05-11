package ch.heigvd.pro.a03.warentities.turrets;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.Point;

public abstract class Turret extends Structure {

    public final WarEntityType.TurretType TYPE;

    public Turret(String name, Point position, int totalHealth,
                  int defPoint, int attackCoolDown, int attackPoints,
                  int range, int price, WarEntityType.TurretType type) {

        super(name,position,totalHealth,defPoint,attackCoolDown);
        super.setRange(range);
        super.setAttackPoints(attackPoints);
        super.setPrice(price);

        TYPE = type;
    }



}
