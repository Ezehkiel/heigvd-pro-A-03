package ch.heigvd.pro.a03.actions;

import ch.heigvd.pro.a03.warentities.units.UnitType;

public class UnitAction {
     UnitActionType unitActionType;
     int quantity;
     UnitType UnitType;

    public UnitAction(UnitActionType unitAction, int quantity, UnitType UnitType) {
        this.unitActionType = unitAction;
        this.quantity = quantity;
        this.UnitType = UnitType;
    }

    public UnitActionType getUnitAction() {
        return unitActionType;
    }

    public int getQuantity() {
        return quantity;
    }

    public ch.heigvd.pro.a03.warentities.units.UnitType getUnitType() {
        return UnitType;
    }

    @Override
    public String toString() {
        return "UnitAction{" +
                "unitActionType=" + unitActionType +
                ", quantity=" + quantity +
                ", UnitType=" + UnitType +
                '}';
    }
}
