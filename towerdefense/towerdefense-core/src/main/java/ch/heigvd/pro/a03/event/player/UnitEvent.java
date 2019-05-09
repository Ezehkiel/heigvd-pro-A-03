package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.warentities.WarEntityType;

public class UnitEvent extends PlayerEvent {
    UnitEventType unitEventType;
    WarEntityType.UnitType unitType;

    public UnitEvent(int entityId, UnitEventType unitEventType, WarEntityType.UnitType unitType) {
        super(entityId);
        this.unitEventType = unitEventType;
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        return "UnitEvent{" +
                "unitEventType=" + unitEventType +
                '}';
    }
}
