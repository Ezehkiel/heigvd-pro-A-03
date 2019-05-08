package ch.heigvd.pro.a03.event.simulation;

import ch.heigvd.pro.a03.event.Event;
import java.io.Serializable;

public class SimEvent extends Event implements Serializable {

    int ticId;
    SimEventType eventType;
    int entityId;

    public SimEvent(int ticId, SimEventType eventType, int entityId) {
        this.ticId = ticId;
        this.eventType = eventType;
        this.entityId = entityId;
    }
}
