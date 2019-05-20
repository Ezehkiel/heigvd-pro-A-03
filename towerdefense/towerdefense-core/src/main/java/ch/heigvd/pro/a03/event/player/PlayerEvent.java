package ch.heigvd.pro.a03.event.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.SocketException;
import java.util.LinkedList;

public class PlayerEvent implements Serializable {

    private LinkedList<TurretEvent> turretEvents;
    private LinkedList<UnitEvent> unitEvents;

    public PlayerEvent() {
        this.turretEvents = new LinkedList<>();
        this.unitEvents = new LinkedList<>();
    }

    public void addTurretEvent(TurretEvent turretEvent){
        turretEvents.add(turretEvent);
    }

    public void addUnitEvent(UnitEvent unitEvent){
        unitEvents.add(unitEvent);
    }
    
    public LinkedList<TurretEvent> getTurretEvents() {
        return turretEvents;
    }

    public LinkedList<UnitEvent> getUnitEvents() {
        return unitEvents;
    }
}
