package ch.heigvd.pro.a03.event.simulation;

import java.awt.*;

public class MoveEvent extends SimEvent  {
    Point destinationPoint;

    public MoveEvent(int ticId, int entityId, Point destinationPoint,int map_id) {
        super(ticId, SimEventType.MOVE, entityId, map_id);
        this.destinationPoint = destinationPoint;
    }

    @Override
    public String toString() {
        return super.toString() + ", dest: " + destinationPoint.toString();
    }
}
