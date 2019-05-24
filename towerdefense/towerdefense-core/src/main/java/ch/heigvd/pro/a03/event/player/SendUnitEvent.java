package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.warentities.WarEntityType;

/**
 * Object created when the player whant to send a quantity of a unit to an another player
 * @Author Didier Page
 */
public class SendUnitEvent extends UnitEvent {

    private int playerIdDestination;
    private int quantity;

    /**
     * Constructor
     * @param playerIdDestination id of the destination player (The id is the in game id)
     * @param unitType The type of unit to send
     * @param quantity the quantity
     */
    public SendUnitEvent( int playerIdDestination, WarEntityType.UnitType unitType, int quantity) {
        super(UnitEventType.SEND,unitType);
        this.playerIdDestination = playerIdDestination;
        this.quantity = quantity;
    }

    /**
     * Getter for the desinationId
     * @return the id
     */
    public int getPlayerIdDestination() {
        return playerIdDestination;
    }

    /**
     * Getter for the quantity
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }
}

