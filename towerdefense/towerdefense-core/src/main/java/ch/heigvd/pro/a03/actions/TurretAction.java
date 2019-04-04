package ch.heigvd.pro.a03.actions;

import javax.swing.text.Position;

public class TurretAction {

    Position turretPosition; // Used to identify the turret
    TurretActionType actionType;

    public TurretAction(Position turretPosition, TurretActionType actionType) {
        this.turretPosition = turretPosition;
        this.actionType = actionType;
    }

    public Position getTurretPosition() {
        return turretPosition;
    }

    public void setTurretPosition(Position turretPosition) {
        this.turretPosition = turretPosition;
    }

    public TurretActionType getActionType() {
        return actionType;
    }

    public void setActionType(TurretActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "TurretAction{" +
                "turretPosition=" + turretPosition +
                ", actionType=" + actionType +
                '}';
    }
}
