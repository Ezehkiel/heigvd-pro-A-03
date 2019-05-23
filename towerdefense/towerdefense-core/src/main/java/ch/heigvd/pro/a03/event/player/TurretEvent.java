package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.warentities.WarEntityType;

import java.awt.*;
import java.io.Serializable;

/**
 * A class used to modelize a turretEvent
 * @Author Didier Page
 */
public class TurretEvent implements Serializable {

    TurretEventType turretEventType;
    Point turretPosition;
    WarEntityType.TurretType turretType;

    /**
     * Constructor
     * @param turretEventType The type of the turret
     * @param turretPosition the position (used to identify them)
     * @param turretType The type of the turret
     */
    public TurretEvent( TurretEventType turretEventType, Point turretPosition, WarEntityType.TurretType turretType) {
        this.turretEventType = turretEventType;
        this.turretPosition = turretPosition;
        this.turretType = turretType;
    }

    /**
     * Getter for the position (Sort of unique id)
     * @return the position
     */
    public Point getTurretPosition() {
        return turretPosition;
    }

    /**
     * Getter for the turret type
     * @return the type
     */
    public WarEntityType.TurretType getTurretType() {
        return turretType;
    }
}
