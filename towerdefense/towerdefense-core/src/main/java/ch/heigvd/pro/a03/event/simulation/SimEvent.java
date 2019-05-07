package ch.heigvd.pro.a03.event.simulation;

import java.io.Serializable;

public class SimEvent implements Serializable {

    int ticId;
    SimEventType eventType;
    int entityId;

    public SimEvent(int ticId, SimEventType eventType, int entityId) {
        this.ticId = ticId;
        this.eventType = eventType;
        this.entityId = entityId;
    }
}
