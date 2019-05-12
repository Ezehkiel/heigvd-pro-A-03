package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.event.Event;
import ch.heigvd.pro.a03.warentities.WarEntityType;

public class UnitEvent extends Event {
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
