package ch.heigvd.pro.a03.event.simulation;

public class AttackEvent extends SimEvent  {

    public final int TARGET_ID;
    public final int DAMAGE;

    public AttackEvent(int ticId, int entityId, int targetId, int dammage) {
        super(ticId, SimEventType.ATTACK, entityId);
        this.TARGET_ID = targetId;
        this.DAMAGE = dammage;
    }

    @Override
    public String toString() {
        return super.toString() + ", target: " + TARGET_ID + ", damage: " + DAMAGE;
    }
}
