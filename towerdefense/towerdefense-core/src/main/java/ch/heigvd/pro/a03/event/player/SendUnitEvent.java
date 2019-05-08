package ch.heigvd.pro.a03.event.player;

public class SendUnitEvent extends UnitEvent {
    int playerIdDestination;

    public SendUnitEvent(int entityId, int playerIdDestination) {
        super(entityId,UnitEventType.SEND);
        this.playerIdDestination = playerIdDestination;
    }

    @Override
    public String toString() {
        return "SendUnitEvent{" +
                "playerIdDestination=" + playerIdDestination +
                '}';
    }
}

