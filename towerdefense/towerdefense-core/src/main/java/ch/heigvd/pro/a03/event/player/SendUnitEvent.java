package ch.heigvd.pro.a03.event.player;

public class SendUnitEvent extends UnitEvent {
    int playerIdDestination;

    public SendUnitEvent(int entityId, UnitEventType unitEventType, int playerIdDestination) {
        super(entityId, unitEventType);
        this.playerIdDestination = playerIdDestination;
    }
}

