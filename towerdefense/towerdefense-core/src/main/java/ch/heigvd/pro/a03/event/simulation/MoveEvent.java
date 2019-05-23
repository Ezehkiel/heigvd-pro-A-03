package ch.heigvd.pro.a03.event.simulation;

import java.awt.*;

/**
 * Move event is when a unit change the position on the map
 * @Author Didier Page
 */
public class MoveEvent extends SimEvent  {

    public final Point DESTINATION_POINT;

    /**
     * Constructor
     * @param ticId The id of the simulation tic
     * @param entityId the entity id (unit)
     * @param destinationPoint the new point
     * @param map_id the id of the map where the event appends
     */
    public MoveEvent(int ticId, int entityId, Point destinationPoint,int map_id) {
        super(ticId, SimEventType.MOVE, entityId, map_id);
            this.DESTINATION_POINT = destinationPoint;
    }

    /**
     * @return Object parsed to string
     */
    @Override
    public String toString() {
        return super.toString() + ", dest: (" + DESTINATION_POINT.x + ", " + DESTINATION_POINT.y + ")";
    }
}
