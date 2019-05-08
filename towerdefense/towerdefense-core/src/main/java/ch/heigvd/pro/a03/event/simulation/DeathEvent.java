package ch.heigvd.pro.a03.event.simulation;

public class DeathEvent extends SimEvent {
    public DeathEvent(int ticId, int entityId) {
        super(ticId, SimEventType.DEATH, entityId);
    }
}
