package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.io.Serializable;

public class UnitEvent implements Serializable {
    UnitEventType unitEventType;
    WarEntityType.UnitType unitType;

    public UnitEvent (UnitEventType unitEventType, WarEntityType.UnitType unitType) {

        this.unitEventType = unitEventType;
        this.unitType = unitType;
    }

    public UnitEventType getUnitEventType() {
        return unitEventType;
    }

    public WarEntityType.UnitType getUnitType() {
        return unitType;
    }

}
