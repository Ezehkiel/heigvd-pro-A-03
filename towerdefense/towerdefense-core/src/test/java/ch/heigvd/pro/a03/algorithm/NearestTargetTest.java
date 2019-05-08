package ch.heigvd.pro.a03.algorithm;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.WarEntity;
import ch.heigvd.pro.a03.warentities.turrets.MachineGunTurret;
import ch.heigvd.pro.a03.warentities.units.Soldier;
import ch.heigvd.pro.a03.warentities.units.Unit;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public class NearestTargetTest {

    @Test
    void NearestNeighborTest() {
//Map used for the test

// 0 1 2 3 4 5 6
// 0 - - - - - - -
// 1 - - - - - - -
// 2 - SOL - - MGT - -
// 3 - - - - MGT - -
// 4 - MGT - - - - -
// 5 - - - - - - B

//SOL: Soldier
//MGT MachineFunTurret
//B: Base


//We create a Map with some WarEntities into it: (as described in previous comments)

//Create a map
        Base base = new Base("Superbase", new Point(6, 5), 1000, 5, 1);
        Map map = new Map(6, 7, base);
//add a Soldier and some turrets to the map
        Soldier soldier = new Soldier("Soldier", new Point(1, 2), 50, 0, 1, 1, 1000, 5, 12);
        MachineGunTurret mgt1 = new MachineGunTurret("mgt1", new Point(1, 4), 700, 100, 1, 5, 2, 40);
        MachineGunTurret mgt2 = new MachineGunTurret("mgt2", new Point(4, 3), 700, 100, 1, 5, 2, 40);
        MachineGunTurret mgt3 = new MachineGunTurret("mgt3", new Point(4, 2), 700, 100, 1, 5, 2, 40);
        map.setStructureAt(mgt1, mgt1.getPosition().y, mgt1.getPosition().x);
        map.setStructureAt(mgt2, mgt2.getPosition().y, mgt2.getPosition().x);
        map.setStructureAt(mgt3, mgt3.getPosition().y, mgt3.getPosition().x);
        LinkedList<Unit> units = new LinkedList<>();
        units.add(soldier);
        map.setUnits(units);

        NearestTarget nt = new NearestTarget(map);
        assert (nt.getNearestTarget(soldier) == mgt1);


    }

}