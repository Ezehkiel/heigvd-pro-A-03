package ch.heigvd.pro.a03.event.player;

import java.awt.*;

public class TurretEvent extends PlayerEvent{

    TurretEventType turretEventType;
    Point turretPosition;

    public TurretEvent(int entityId, TurretEventType turretEventType,Point turretPosition) {
        super(entityId);
        this.turretEventType = turretEventType;
        this.turretPosition = turretPosition;
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
