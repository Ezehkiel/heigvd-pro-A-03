package ch.heigvd.pro.a03.event.simulation;

import java.awt.*;

public class MoveEvent extends SimEvent  {

    public final Point DESTINATION_POINT;

    public MoveEvent(int ticId, int entityId, Point destinationPoint) {
        super(ticId, SimEventType.MOVE, entityId);
        this.DESTINATION_POINT = destinationPoint;
    }

    @Override
    public String toString() {
        return super.toString() + ", dest: (" + DESTINATION_POINT.x + ", " + DESTINATION_POINT.y + ")";
    }
}
