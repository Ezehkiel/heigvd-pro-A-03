package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.warentities.WarEntityType;

public class SendUnitEvent extends UnitEvent {
    int playerIdDestination;

    public SendUnitEvent( int playerIdDestination, WarEntityType.UnitType unitType) {
        super(UnitEventType.SEND,unitType);
        this.playerIdDestination = playerIdDestination;
    }

    public int getPlayerIdDestination() {
        return playerIdDestination;
    }
}

