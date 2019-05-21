package ch.heigvd.pro.a03.warentities;

import ch.heigvd.pro.a03.warentities.turrets.LaserGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.MachineGunTurret;
import ch.heigvd.pro.a03.warentities.turrets.MortarTurret;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import ch.heigvd.pro.a03.warentities.units.Scoot;
import ch.heigvd.pro.a03.warentities.units.Soldier;
import ch.heigvd.pro.a03.warentities.units.Tank;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;

public interface WarEntityType {
    enum UnitType implements WarEntityType {

        SCOOT("Scout") {
            @Override
            public Unit createUnit(Point p) {
                return new Scoot(p);
            }
        }, SOLIDER("Soldier") {
            @Override
            public Unit createUnit(Point p) {
                return new Soldier(p);
            }
        }, TANK("Tank") {
            @Override
            public Unit createUnit(Point p) {
                return new Tank(p);
            }
        };

        private final String name;

        UnitType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public abstract Unit createUnit(Point p);
    }

    enum TurretType implements WarEntityType {
        MACHINE_GUN("MG") {
            @Override
            public Turret createTurret(Point p) {
                return new MachineGunTurret(p);
            }
        }, MORTAR("MR") {
            @Override
            public Turret createTurret(Point p) {
                return new MortarTurret(p);
            }
        }, LASER_GUN("LG") {
            @Override
            public Turret createTurret(Point p) {
                return new LaserGunTurret(p);
            }
        };

        private final String name;

        TurretType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public abstract Turret createTurret(Point p);
    }
}
