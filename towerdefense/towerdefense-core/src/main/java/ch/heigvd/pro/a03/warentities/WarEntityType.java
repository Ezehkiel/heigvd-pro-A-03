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
        SCOOT{
            @Override
            public Unit createUnit(Point p) {
                return new Scoot(p);
            }
        },SOLIDER {
            @Override
            public Unit createUnit(Point p) {
                return new Soldier(p);
            }
        },TANK{
            @Override
            public Unit createUnit(Point p) {
                return new Tank(p);
            }
        };

        public  abstract Unit createUnit(Point p);
    }
    enum TurretType implements WarEntityType {
        MACHINE_GUN{
            @Override
            public Turret createTurret(Point p) {
                return new MachineGunTurret(p);
            }
        },MORTAR{
            @Override
            public Turret createTurret(Point p) {
                return new MortarTurret(p);
            }
        },LASER_GUN{
            @Override
            public Turret createTurret(Point p) {
                return new LaserGunTurret(p);
            }
        };

        public abstract Turret createTurret(Point p);

    }
}
