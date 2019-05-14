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



        //Create a map
        Base base = new Base("Superbase", new Point(6, 5), 100000, 5, 1);
        Map map = new Map(6, 7, base,new Point(11,4),0);
        //add a Soldier and some turrets to the map
        Soldier soldier = new Soldier("Soldier", new Point(1, 2), 5000, 0, 1, 1, 1000, 5, 12);
        Soldier soldier1 = new Soldier("Soldier1", new Point(0, 0), 500, 0, 1, 1, 10000, 5, 12);
        Soldier soldier2 = new Soldier("Soldier2", new Point(2, 0), 500, 0, 1, 1, 10000, 5, 12);


        MachineGunTurret mgt1 = new MachineGunTurret("mgt1", new Point(1, 4), 700, 100, 1, 5, 2, 40);
        MachineGunTurret mgt2 = new MachineGunTurret("mgt2", new Point(4, 3), 700, 100, 1, 5, 2, 40);
        MachineGunTurret mgt3 = new MachineGunTurret("mgt3", new Point(4, 2), 700, 100, 1, 5, 2, 40);
        map.setStructureAt(mgt1, mgt1.getPosition().y, mgt1.getPosition().x);
        map.setStructureAt(mgt2, mgt2.getPosition().y, mgt2.getPosition().x);
        map.setStructureAt(mgt3, mgt3.getPosition().y, mgt3.getPosition().x);
        LinkedList<Unit> units = new LinkedList<>();
        units.add(soldier);
        units.add(soldier1);
        units.add(soldier2);
        map.setUnits(units);
        System.out.println("-------------------------------");

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("tower position: "+map.getStructures()[i][j].getPosition().x + "  " +
                            map.getStructures()[i][j].getPosition().y);
                }

            }
        }

        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y );

        }
        //###### iteration 0 ######


        checkHelper(map, soldier, new Point(1, 2));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));


        //###### iteration 1 ######
        System.out.println("--------iteration 1--------");

        map.update(0);

        System.out.println("full path to Base:");
        System.out.println(soldier.getPath()+"\n");


        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y +"\n");
            System.out.println("is the entity destroyed?: "+u.isEntityDestroyed());
            System.out.println("health points: "+u.getHealthPoint());
        }

        System.out.println();

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("is the tower destroyed?: " + map.getStructures()[i][j].isEntityDestroyed());
                    System.out.println("tower hp: " + map.getStructures()[i][j].getHealthPoint());
                }

            }
        }


        checkHelper(map, soldier, new Point(2, 2));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));


        //###### iteration 2 ######
        System.out.println("--------iteration 2--------");
        map.update(0);

        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y +"\n");
            System.out.println("is the entity destroyed?: "+u.isEntityDestroyed());
            System.out.println("health points: "+u.getHealthPoint());
        }

        System.out.println();

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("is the tower destroyed?: " + map.getStructures()[i][j].isEntityDestroyed());
                    System.out.println("tower hp: " + map.getStructures()[i][j].getHealthPoint());
                }

            }
        }



        checkHelper(map, soldier, new Point(2, 3));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));


        //###### iteration 3 ######
        System.out.println("--------iteration 3--------");
        map.update(0);

        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y +"\n");
            System.out.println("is the entity destroyed?: "+u.isEntityDestroyed());
            System.out.println("health points: "+u.getHealthPoint());
        }

        System.out.println();

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("is the tower destroyed?: " + map.getStructures()[i][j].isEntityDestroyed());
                    System.out.println("tower hp: " + map.getStructures()[i][j].getHealthPoint());
                }

            }
        }


        checkHelper(map, soldier, new Point(3, 3));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));


        //###### iteration 4 ######
        System.out.println("--------iteration 4--------");
        map.update(0);

        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y +"\n");
            System.out.println("is the entity destroyed?: "+u.isEntityDestroyed());
            System.out.println("health points: "+u.getHealthPoint());
        }

        System.out.println();

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("is the tower destroyed?: " + map.getStructures()[i][j].isEntityDestroyed());
                    System.out.println("tower hp: " + map.getStructures()[i][j].getHealthPoint());
                }

            }
        }


        checkHelper(map, soldier, new Point(3, 4));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));


        //###### iteration 5 ######
        System.out.println("--------iteration 5--------");
        map.update(0);

        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y +"\n");
            System.out.println("is the entity destroyed?: "+u.isEntityDestroyed());
            System.out.println("health points: "+u.getHealthPoint());
        }

        System.out.println();

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("is the tower destroyed?: " + map.getStructures()[i][j].isEntityDestroyed());
                    System.out.println("tower hp: " + map.getStructures()[i][j].getHealthPoint());
                }

            }
        }

        checkHelper(map, soldier, new Point(4, 4));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));


        //###### iteration 6 ######
        System.out.println("--------iteration 6--------");

        map.update(0);

        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y +"\n");
            System.out.println("is the entity destroyed?: "+u.isEntityDestroyed());
            System.out.println("health points: "+u.getHealthPoint());
        }

        System.out.println();

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("is the tower destroyed?: " + map.getStructures()[i][j].isEntityDestroyed());
                    System.out.println("tower hp: " + map.getStructures()[i][j].getHealthPoint());
                }

            }
        }


        checkHelper(map, soldier, new Point(5, 4));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));


        //###### iteration 7 ######

        System.out.println("--------iteration 7--------");
        map.update(0);

        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y +"\n");
            System.out.println("is the entity destroyed?: "+u.isEntityDestroyed());
            System.out.println("health points: "+u.getHealthPoint());
        }

        System.out.println();

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("is the tower destroyed?: " + map.getStructures()[i][j].isEntityDestroyed());
                    System.out.println("tower hp: " + map.getStructures()[i][j].getHealthPoint());
                }

            }
        }



        checkHelper(map, soldier, new Point(6, 4));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));


        //###### iteration 8 ######
        System.out.println("--------iteration 8--------");
        map.update(0);

        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y +"\n");
            System.out.println("is the entity destroyed?: "+u.isEntityDestroyed());
            System.out.println("health points: "+u.getHealthPoint());
        }

        System.out.println();

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("is the tower destroyed?: " + map.getStructures()[i][j].isEntityDestroyed());
                    System.out.println("tower hp: " + map.getStructures()[i][j].getHealthPoint());
                }

            }
        }


        checkHelper(map, soldier, new Point(6, 4));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));


        //###### iteration 9 ######
        System.out.println("--------iteration 9--------");
        map.update(0);

        for(Unit u: units){
            System.out.println("Unit position "+u.getPosition().x +" "+ u.getPosition().y +"\n");
            System.out.println("is the entity destroyed?: "+u.isEntityDestroyed());
            System.out.println("health points: "+u.getHealthPoint());
        }

        System.out.println();

        for(int i =0; i<map.getRow();++i) {
            for (int j = 0; j <map.getCol(); ++j) {

                if(map.getStructures()[i][j]!=null) {
                    System.out.println("is the tower destroyed?: " + map.getStructures()[i][j].isEntityDestroyed());
                    System.out.println("tower hp: " + map.getStructures()[i][j].getHealthPoint());
                }

            }
        }

        checkHelper(map, soldier, new Point(6, 4));
        checkHelper(map, mgt1, new Point(1, 4));
        checkHelper(map, mgt2, new Point(4, 3));
        checkHelper(map, mgt3, new Point(4, 2));

    }


    private void checkHelper(Map map, WarEntity we, Point expectedPosition) throws Exception {

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
            assertEquals(unit.getPosition(), expectedPosition);

        } else if (we instanceof Structure) {

            Structure structure = (Structure) we;


            assertSame(structure, map.getStructureAt(expectedPosition.y, expectedPosition.x));
            assertEquals(structure.getPosition(), expectedPosition);



        } else throw new Exception("CheckHelper unsupported check request");

    }


}
