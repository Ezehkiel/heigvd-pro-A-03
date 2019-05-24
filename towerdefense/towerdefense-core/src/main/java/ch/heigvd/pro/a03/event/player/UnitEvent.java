package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.io.Serializable;

/**
 * A class used to modelize a unit event
 * @Author Didier Page
 */
public class UnitEvent implements Serializable {

    UnitEventType unitEventType;
    WarEntityType.UnitType unitType;

    /**
     * Constructor
     * @param unitEventType the type of the event
     * @param unitType the type of the unit
     */
    public UnitEvent (UnitEventType unitEventType, WarEntityType.UnitType unitType) {

        this.unitEventType = unitEventType;
        this.unitType = unitType;
    }

    /**
     * Getter for the unitType
     * @return the type of the unit
     */
    public WarEntityType.UnitType getUnitType() {
        return unitType;
    }

}
