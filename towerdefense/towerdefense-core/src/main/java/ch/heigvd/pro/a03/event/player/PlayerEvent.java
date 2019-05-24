package ch.heigvd.pro.a03.event.player;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Class used to transfert Unit and turret event from the client
 * @Author Didier Page
 */
public class PlayerEvent implements Serializable {

    private LinkedList<TurretEvent> turretEvents;
    private LinkedList<UnitEvent> unitEvents;

    /**
     * Construcotr
     */
    public PlayerEvent() {
        this.turretEvents = new LinkedList<>();
        this.unitEvents = new LinkedList<>();
    }

    /**
     * Add a turret event to the liste
     * @param turretEvent event to add
     */
    public void addTurretEvent(TurretEvent turretEvent){
        turretEvents.add(turretEvent);
    }

    /**
     * add a unit event to the list
     * @param unitEvent event to add
     */
    public void addUnitEvent(UnitEvent unitEvent){
        unitEvents.add(unitEvent);
    }

    /**
     * Getter for turret events
     * @return the list of turret events
     */
    public LinkedList<TurretEvent> getTurretEvents() {
        return turretEvents;
    }

    /**
     * Getter for unit events
     * @return the list of unit events
     */
    public LinkedList<UnitEvent> getUnitEvents() {
        return unitEvents;
    }
}
