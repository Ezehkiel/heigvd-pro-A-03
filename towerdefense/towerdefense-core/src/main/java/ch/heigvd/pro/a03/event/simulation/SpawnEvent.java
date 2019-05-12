package ch.heigvd.pro.a03.event.simulation;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;


public class SpawnEvent extends SimEvent {

    WarEntityType.UnitType unitType;
    Point spawnPoint;

    public SpawnEvent(int ticId, int entityId, WarEntityType.UnitType unitType, Point spawnPoint) {
        super(ticId, SimEventType.SPAWN, entityId);
        this.unitType =  unitType;
        this.spawnPoint = spawnPoint;
    }

    @Override
    public String toString() {
        return super.toString() + ", dest: " + spawnPoint.toString() + ", type: " + unitType;
    }
}
