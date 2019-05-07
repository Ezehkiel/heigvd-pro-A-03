package ch.heigvd.pro.a03.event.simulation;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;


public class SpawnEvent extends SimEvent {

    WarEntityType EntityType;
    Point spawnPoint;

    public SpawnEvent(int ticId, SimEventType eventType, int entityId, WarEntityType entityType, Point spawnPoint) {
        super(ticId, eventType, entityId);
        EntityType = entityType;
        this.spawnPoint = spawnPoint;
    }
}
