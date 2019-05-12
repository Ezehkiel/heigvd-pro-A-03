package ch.heigvd.pro.a03.event.simulation;

public class DeathEvent extends SimEvent {
    public DeathEvent(int ticId, int entityId ,int map_id) {
        super(ticId, SimEventType.DEATH, entityId, map_id);
    }
}
