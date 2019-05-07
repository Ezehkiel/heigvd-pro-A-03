package ch.heigvd.pro.a03.event.simulation;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;


public class SpawnEvent extends SimEvent {

    WarEntityType EntityType;
    Point spawnPoint;

    public SpawnEvent(int ticId, int entityId, WarEntityType entityType, Point spawnPoint) {
        super(ticId, SimEventType.SPAWN, entityId);
        EntityType = entityType;
        this.spawnPoint = spawnPoint;
    }
}
