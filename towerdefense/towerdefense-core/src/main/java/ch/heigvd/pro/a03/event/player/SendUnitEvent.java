package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.warentities.WarEntityType;

public class SendUnitEvent extends UnitEvent {

    private int playerIdDestination;
    private int quantity;

    public SendUnitEvent( int playerIdDestination, WarEntityType.UnitType unitType, int quantity) {
        super(UnitEventType.SEND,unitType);
        this.playerIdDestination = playerIdDestination;
        this.quantity = quantity;
    }

    public int getPlayerIdDestination() {
        return playerIdDestination;
    }

    public int getQuantity() {
        return quantity;
    }
}

