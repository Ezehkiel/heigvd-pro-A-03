package ch.heigvd.pro.a03.event.player;

import java.awt.*;

public class TurretEvent extends PlayerEvent{

    TurretEventType turretEventType;
    Point turretPosition;

    public TurretEvent(int entityId, TurretEventType turretEventType) {
        super(entityId);
        this.turretEventType = turretEventType;
    }
}
