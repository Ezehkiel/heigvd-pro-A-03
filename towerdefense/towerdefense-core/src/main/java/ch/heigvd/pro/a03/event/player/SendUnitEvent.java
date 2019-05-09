package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.warentities.WarEntityType;

public class SendUnitEvent extends UnitEvent {
    int playerIdDestination;

    public SendUnitEvent(int entityId, int playerIdDestination, WarEntityType.UnitType unitType) {
        super(entityId,UnitEventType.SEND,unitType);
        this.playerIdDestination = playerIdDestination;
    }

    @Override
    public String toString() {
        return "SendUnitEvent{" +
                "playerIdDestination=" + playerIdDestination +
                '}';
    }
}

