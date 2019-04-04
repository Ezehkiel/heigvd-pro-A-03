package ch.heigvd.pro.a03.actions;

import java.util.ArrayList;

public class PlayerEvent {

    ArrayList<UnitAction> unitActions;
    //ArrayList<TurretAction> turretActions;

    public PlayerEvent(ArrayList<UnitAction> unitActions/*, ArrayList<TurretAction> turretActions*/) {
        this.unitActions = unitActions;
        //this.turretActions = turretActions;
    }

    @Override
    public String toString() {
        return "PlayerEvent{" +
                "unitActions=" + unitActions +
                ", turretActions="+
                '}';
    }
}
