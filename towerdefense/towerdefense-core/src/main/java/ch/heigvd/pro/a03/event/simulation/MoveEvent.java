package ch.heigvd.pro.a03.event.simulation;

import java.awt.*;

public class MoveEvent extends SimEvent  {
    Point destinationPoint;

    public MoveEvent(int ticId, int entityId, Point destinationPoint) {
        super(ticId, SimEventType.MOVE, entityId);
        this.destinationPoint = destinationPoint;
    }

    @Override
    public String toString() {
        return super.toString() + ", dest: " + destinationPoint.toString();
    }
}
