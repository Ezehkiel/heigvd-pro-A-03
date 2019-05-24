package ch.heigvd.pro.a03.event.simulation;

/**
 * A death event is when an entity is destroyed
 * @Author Didier Page
 */
public class DeathEvent extends SimEvent {
    /**
     * Constructor
     * @param ticId The id of the simulation tic
     * @param entityId the entity id (Turret or unit)
     * @param map_id the id of the map where the event appends
     */
    public DeathEvent(int ticId, int entityId ,int map_id) {
        super(ticId, SimEventType.DEATH, entityId, map_id);
    }
}
