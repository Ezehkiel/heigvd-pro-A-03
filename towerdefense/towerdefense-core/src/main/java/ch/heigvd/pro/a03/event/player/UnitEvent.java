package ch.heigvd.pro.a03.event.player;

public class UnitEvent extends PlayerEvent {
    UnitEventType unitEventType;

    public UnitEvent(int entityId, UnitEventType unitEventType) {
        super(entityId);
        this.unitEventType = unitEventType;
    }

    @Override
    public String toString() {
        return "UnitEvent{" +
                "unitEventType=" + unitEventType +
                '}';
    }
}
