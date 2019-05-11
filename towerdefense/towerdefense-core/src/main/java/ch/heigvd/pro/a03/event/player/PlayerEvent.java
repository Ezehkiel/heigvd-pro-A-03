package ch.heigvd.pro.a03.event.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

    public static void sendPlayerEvent(PlayerEvent event, ObjectOutputStream out) {
        try {
            out.writeObject(event);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerEvent getPlayerEvent(ObjectInputStream in) {
        PlayerEvent event = null;
        try {
            event = (PlayerEvent) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public LinkedList<TurretEvent> getTurretEvents() {
        return turretEvents;
    }

    public LinkedList<UnitEvent> getUnitEvents() {
        return unitEvents;
    }

    public void clear() {
        unitEvents.clear();
        turretEvents.clear();
    }
}
