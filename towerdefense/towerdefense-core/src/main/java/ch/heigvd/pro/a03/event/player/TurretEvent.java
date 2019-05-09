package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;

public class TurretEvent extends PlayerEvent{

    TurretEventType turretEventType;
    Point turretPosition;
    WarEntityType.TurretType turretType;

    public TurretEvent(int entityId, TurretEventType turretEventType, Point turretPosition, WarEntityType.TurretType turretType) {
        super(entityId);
        this.turretEventType = turretEventType;
        this.turretPosition = turretPosition;
        this.turretType = turretType;
    }

    @Override
    public String toString() {
        return "TurretEvent{" +
                "turretEventType=" + turretEventType +
                ", turretPosition=" + turretPosition +
                ", entityId=" + entityId +
                '}';
    }
}
