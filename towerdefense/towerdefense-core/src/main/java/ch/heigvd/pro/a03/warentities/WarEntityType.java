package ch.heigvd.pro.a03.warentities;

public interface WarEntityType {
    enum UnitType implements WarEntityType {
        SCOOT,SOLIDER,TANK
    }
    enum TurretType implements WarEntityType {
        MACHINEGUN,MORTAR,SLOWER
    }
}
