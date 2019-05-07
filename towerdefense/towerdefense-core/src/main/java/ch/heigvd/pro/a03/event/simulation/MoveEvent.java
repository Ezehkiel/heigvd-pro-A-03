package ch.heigvd.pro.a03.event.simulation;

import java.awt.*;

public class MoveEvent extends SimEvent  {
    Point destinationPoint;

    public MoveEvent(int ticId, SimEventType eventType, int entityId, Point destinationPoint) {
        super(ticId, eventType, entityId);
        this.destinationPoint = destinationPoint;
    }

}
