package ch.heigvd.pro.a03.event.simulation;

/**
 * An attack event is when a turret attacks a target
 * @Author Didier Page
 */
public class AttackEvent extends SimEvent  {

    public final int TARGET_ID;
    public final int DAMAGE;

    /**
     * Constructor
     * @param ticId The id of the simulation tic
     * @param entityId the entity id (Turret or unit)
     * @param targetId the entity id (Turret or unit)
     * @param dammage the quantiy HP that remove the attack
     * @param map_id the id of the map where the event appends
     */
    public AttackEvent(int ticId, int entityId, int targetId, int dammage,int map_id) {
        super(ticId, SimEventType.ATTACK, entityId, map_id);
            this.TARGET_ID = targetId;
            this.DAMAGE = dammage;
    }

    /**
     * @return Object parsed to string
     */
    @Override
    public String toString() {
        return super.toString() + ", target: " + TARGET_ID + ", damage: " + DAMAGE;
    }
}
