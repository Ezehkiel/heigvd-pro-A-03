package ch.heigvd.pro.a03.event.simulation;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;

/**
 * Spawn event is when a unit is create on the map
 * @Author Didier Page
 */
public class SpawnEvent extends SimEvent {

    public final WarEntityType.UnitType UNIT_TYPE;
    public final Point SPAWN_POINT;

    /**
     * Constructor
     * @param ticId The id of the simulation tic
     * @param entityId the entity id (unit)
     * @param unitType the type of unit
     * @param spawnPoint the position where the unit spawn
     * @param map_id the id of the map where the event appends
     */
    public SpawnEvent(int ticId, int entityId, WarEntityType.UnitType unitType, Point spawnPoint,int map_id) {
        super(ticId, SimEventType.SPAWN, entityId, map_id);
        this.UNIT_TYPE = unitType;
        this.SPAWN_POINT = spawnPoint;
    }

    /**
     * @return Object parsed to string
     */
    @Override
    public String toString() {
        return super.toString() + ", dest: (" + SPAWN_POINT.x + ", " + SPAWN_POINT.y + "), type: " + UNIT_TYPE.name();
    }
}
