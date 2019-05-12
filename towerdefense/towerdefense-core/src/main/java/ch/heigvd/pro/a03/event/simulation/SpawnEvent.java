package ch.heigvd.pro.a03.event.simulation;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;


public class SpawnEvent extends SimEvent {

    public final WarEntityType.UnitType UNIT_TYPE;
    public final Point SPAWN_POINT;

    public SpawnEvent(int ticId, int entityId, WarEntityType.UnitType unitType, Point spawnPoint,int map_id) {
        super(ticId, SimEventType.SPAWN, entityId, map_id);
        this.UNIT_TYPE = unitType;
        this.SPAWN_POINT = spawnPoint;
    }

    @Override
    public String toString() {
        return super.toString() + ", dest: (" + SPAWN_POINT.x + ", " + SPAWN_POINT.y + "), type: " + UNIT_TYPE.name();
    }
}
