package ch.heigvd.pro.a03.event.simulation;

import ch.heigvd.pro.a03.event.Event;
import java.io.Serializable;

public class SimEvent extends Event implements Serializable {

    public final SimEventType TYPE;
    public final int TICK_ID;
    public final int ENTITY_ID;
    public final int MAP_ID;

    public SimEvent(int tickId, SimEventType type, int entityId, int map_id) {
        this.TYPE = type;
        this.TICK_ID = tickId;
        this.ENTITY_ID = entityId;
        MAP_ID = map_id;
    }

    @Override
    public String toString() {
        return String.format("%s of %d at %d on map %s", TYPE.name(), ENTITY_ID, TICK_ID, MAP_ID);
    }
}
