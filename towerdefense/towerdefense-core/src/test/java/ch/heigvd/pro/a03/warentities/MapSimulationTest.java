package ch.heigvd.pro.a03.warentities;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.warentities.turrets.MachineGunTurret;
import ch.heigvd.pro.a03.warentities.units.Soldier;
import ch.heigvd.pro.a03.warentities.units.Unit;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class MapSimulationTest {

    @Test
    public void SimpleTest1() throws Exception {

        //Starting Map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -  SOL  -   -  MGT  -   -
        // 3    -   -   -   -  MGT  -   -
        // 4    -  MGT  -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //B: Base


        //We create a Map with some WarEntities into it: (as described in previous comments)

        //Create a map
        Base base = new Base("Superbase", new Point(6, 5), 1000, 5);
        Map map = new Map(6, 7, base);
        //add a Soldier and some turrets to the map
        Soldier soldier = new Soldier("Soldier", new Point(1, 2), 50, 0, 1, 1000, 5, 12);
        MachineGunTurret mgt1 = new MachineGunTurret("mgt1", new Point(1, 4), 700, 100, 5, 2, 40);
        MachineGunTurret mgt2 = new MachineGunTurret("mgt2", new Point(4, 3), 700, 100, 5, 2, 40);
        MachineGunTurret mgt3 = new MachineGunTurret("mgt3", new Point(4, 2), 700, 100, 5, 2, 40);
        map.setStructureAt(mgt1, mgt1.getPosition().x, mgt1.getPosition().y);
        map.setStructureAt(mgt2, mgt2.getPosition().x, mgt2.getPosition().y);
        map.setStructureAt(mgt3, mgt3.getPosition().x, mgt3.getPosition().y);
        LinkedList<Unit> units = new LinkedList<>();
        units.add(soldier);
        map.setUnits(units);


        //###### iteration 0 ######

        //Here's the current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -  SOL  -   -  MGT  -   -
        // 3    -   -   -   -  MGT  -   -
        // 4    -  MGT  -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 50
        //mgt1 :    700
        //mgt2 :    700
        //mgt3 :    700

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(1, 2), 50);
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 700);
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 700);
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 700);


        //###### iteration 1 ######

        map.update();

        //Here's the new current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -   -  MGT  -   -
        // 3    -  SOL  -   -  MGT  -   -
        // 4    -  MGT  -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 45 (50 - 5)
        //mgt1 :    700
        //mgt2 :    700
        //mgt3 :    700

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(1, 3), 40);
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 700);
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 700);
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 700);


        //###### iteration 2 ######

        map.update();

        //Here's the new current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -   -  MGT  -   -
        // 3    -  SOL  -   -  MGT  -   -
        // 4    -  MGT  -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 40 (45 -5)
        //mgt1 :    200 (700 - 1000*(100/(100 + 100))
        //mgt2 :    700
        //mgt3 :    700

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(1, 3), 40);
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 200);
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 700);
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 700);


        //###### iteration 3 ######

        map.update();

        //Here's the new current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -   -  MGT  -   -
        // 3    -  SOL  -   -  MGT  -   -
        // 4    -   x   -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //x destroyed WarEntity
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 35
        //mgt1 :    0
        //mgt2 :    700
        //mgt3 :    700

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(1, 3), 40);
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 0);
        //we check the turret is dead
        assert (mgt1.isEntityDestroyed());
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 700);
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 700);


        //###### iteration 4 ######

        map.update();

        //Here's the new current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -   -  MGT  -   -
        // 3    -   -  SOL  -  MGT  -   -
        // 4    -   x   -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //x destroyed WarEntity
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 35
        //mgt1 :    0
        //mgt2 :    700
        //mgt3 :    700

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(2, 3), 35);
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 0);
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 700);
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 700);


        //###### iteration 5 ######

        map.update();

        //Here's the new current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -   -  MGT  -   -
        // 3    -   -   -  SOL MGT  -   -
        // 4    -   x   -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //x destroyed WarEntity
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 30
        //mgt1 :    0
        //mgt2 :    700
        //mgt3 :    700

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(3, 3), 30);
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 0);
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 700);
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 700);


        //###### iteration 6 ######

        map.update();

        //Here's the new current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -   -  MGT  -   -
        // 3    -   -   -  SOL MGT  -   -
        // 4    -   x   -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //x destroyed WarEntity
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 20
        //mgt1 :    0
        //mgt2 :    200
        //mgt3 :    700

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(3, 3), 20);
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 0);
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 200);
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 700);


        //###### iteration 7 ######

        map.update();

        //Here's the new current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -   -  MGT  -   -
        // 3    -   -   -  SOL  x   -   -
        // 4    -   x   -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //x destroyed WarEntity
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 10
        //mgt1 :    0
        //mgt2 :    0
        //mgt3 :    700

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(3, 3), 10);
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 0);
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 0);
        //we check the turret is dead
        assert (mgt2.isEntityDestroyed());
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 700);


        //###### iteration 8 ######

        map.update();

        //Here's the new current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -  SOL MGT  -   -
        // 3    -   -   -   -   x   -   -
        // 4    -   x   -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //x destroyed WarEntity
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 5
        //mgt1 :    0
        //mgt2 :    0
        //mgt3 :    700

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(3, 2), 5);
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 0);
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 0);
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 700);


        //###### iteration 8 ######

        map.update();

        //Here's the new current map state

        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -   x  MGT  -   -
        // 3    -   -   -   -   x   -   -
        // 4    -   x   -   -   -   -   -
        // 5    -   -   -   -   -   -   B

        //SOL: Soldier
        //MGT MachineFunTurret
        //x destroyed WarEntity
        //B: Base

        //Here is the health of the WarEntities
        //soldier : 0
        //mgt1 :    0
        //mgt2 :    0
        //mgt3 :    200

        //we check Units positions and WarEntities health is correct
        //(correct means as indicated below)

        //we check soldier is at position (1,2), and has 50 health points
        checkHelper(map, soldier, new Point(3, 2), 0);
        //we check soldier is dead
        assert (soldier.isEntityDestroyed());
        //we check mgt1 is at position (1,4), and has 700 health points
        checkHelper(map, mgt1, new Point(1, 4), 0);
        //we check mgt2 is at position (4,3), and has 50 health points
        checkHelper(map, mgt2, new Point(4, 3), 0);
        //we check mgt3 is at position (4,2), and has 50 health points
        checkHelper(map, mgt3, new Point(4, 2), 200);

    }


    private void checkHelper(Map map, WarEntity we, Point expectedPosition, int expectedHealth) throws Exception {

        if (we instanceof Unit) {

            Unit unit = (Unit) we;

            //we check the Unit is in map
            assert (map.getUnits().size() > 0);
            boolean unitIsInMap = false;
            for (Unit u : map.getUnits()) {
                if (unit == u) {
                    unitIsInMap = true;
                    break;
                }
            }
            assertTrue(unitIsInMap);

            //we check the Unit is at the expected position
            assertEquals(unit.getPosition(), expectedPosition);

            //we check the Unit has the expected remaining health
            assert (unit.getHealthPoint() == expectedHealth);

        } else if (we instanceof Structure) {

            Structure structure = (Structure) we;

            //we check the structure is at the expected position
            assertSame(structure, map.getStructureAt(expectedPosition.x, expectedPosition.y));
            assertEquals(structure.getPosition(), expectedPosition);

            //we check the structure has the expected remaining health
            assert (structure.getHealthPoint() == expectedHealth);


        } else throw new Exception("CheckHelper unsupported check request");

    }


}
