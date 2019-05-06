package ch.heigvd.pro.a03.event.simulation;

public class AttackEvent extends SimEvent  {
    int targetId;
    int dammage;

    public AttackEvent(int ticId, SimEventType eventType, int entityId, int targetId, int dammage) {
        super(ticId, eventType, entityId);
        this.targetId = targetId;
        this.dammage = dammage;
    }
}
