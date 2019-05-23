package ch.heigvd.pro.a03.event.simulation;

import java.io.Serializable;

/**
 * A Simulation Event is an event saved at a moment in the simulation
 * @Author Didier Page
 */
public class SimEvent implements Serializable {

    public final SimEventType TYPE;
    public final int TICK_ID;
    public final int ENTITY_ID;
    public final int MAP_ID;

    /**
     *
     * @param tickId The id of the simulation tic
     * @param type Type of the event
     * @param entityId the entity id (Turret or unit)
     * @param map_id the id of the map where the event appends
     */
    public SimEvent(int tickId, SimEventType type, int entityId, int map_id) {
        this.TYPE = type;
        this.TICK_ID = tickId;
        this.ENTITY_ID = entityId;
        MAP_ID = map_id;
    }

    /**
     * @return Object parsed to string
     */
    @Override
    public String toString() {
        return String.format("%s of %d at %d on map %s", TYPE.name(), ENTITY_ID, TICK_ID, MAP_ID);
    }
}
