package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.event.simulation.SpawnEvent;
import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.WarEntity;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import ch.heigvd.pro.a03.warentities.units.Unit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

/***
 * Class that represents the gird of the game
 * @author Andres Moreno, Nicodeme Stalder
 */
public class Map implements Serializable {

    private Structure[][] structures;
    private LinkedList<Unit> units;
    private LinkedList<Unit> unspawnedUnits;
    private LinkedList<Unit> spawnedUnits;
    private int row;
    private int col;
    private Base base;
    private Point spawnPoint; //position where the units will be generated
    private boolean endMatch;
    public final int ID;

    /***
     * Constructor of the class
     * @param row number of rows of the gird
     * @param col number of columns of the grid
     * @param base the player base
     * @param spawnPoint the position where the enemy units will spawn
     * @param id  the map id
     */
    public Map(int row, int col, Base base,Point spawnPoint, int id) {
        this.row = row;
        this.col = col;
        structures = new Structure[row][col];
        units = new LinkedList<>();
        spawnedUnits = new LinkedList<>();
        unspawnedUnits = new LinkedList<>();
        this.base = base;
        this.spawnPoint = spawnPoint;
        setStructureAt(base, base.getPosition().y, base.getPosition().x);
        ID = id;
    }

    public Base getBase() {
        return base;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /***
     * Get's the structure from a given position.
     * @param row the y coordinate
     * @param col the x coordinate
     * @return the structure at the given position if there is one, otherwise null.
     */
    public Structure getStructureAt(int row, int col) {

        if (!inMap(row, col)) {
            return null;
        }

        return structures[row][col];
    }

    /**
     *
     * @param structure the structure to be set (can be a base or a turret)
     * @param row the y coordinate
     * @param col the x coordinate
     */
    public void setStructureAt(Structure structure, int row, int col) {

        if (!inMap(row, col)) {
            throw new IndexOutOfBoundsException("Position not in map");
        }

        structures[row][col] = structure;

    }

    /**
     * Add's a unit to the map
     * @param u the unit
     */
    public void addUnit(Unit u) {
        units.add(u);
        unspawnedUnits.add(u);
    }


    public Structure[][] getStructures() {
        return structures;
    }

    public Point getSpawnPoint() {
        return spawnPoint;
    }

    public void setUnits(LinkedList<Unit> units) {
        this.units = units;
        this.unspawnedUnits = (LinkedList<Unit>) units.clone();
        this.spawnedUnits.clear();
    }

    public LinkedList<Unit> getUnits() {
        return units;
    }

    /***
     *
     * @param row the y coordinate
     * @param col the x coordinate
     * @return true if the position is inside the map bounds
     */
    public boolean inMap(int row, int col) {
        return (row >= 0 && row < this.row && col >= 0 && col < this.col);
    }

    /***
     * Updates the structures and units of the map (health, position, etc.)
     * @param tickId number of ticks of the match
     */
    public void update(int tickId) {

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (structures[i][j] != null) {
                    structures[i][j].update(tickId, this);
                }
            }
        }

        spawnAUnit(tickId);

        for (Unit u : spawnedUnits) {
            u.update(tickId, this);
        }
    }

    /***
     *
     * @return true if there is no more units alive.
     */
    public boolean unitsAreDead(){

        boolean everyOneIsDead=true;

        for(Unit u :units ){

            if(!u.isEntityDestroyed()){
                everyOneIsDead=false;
            }
        }
        return everyOneIsDead;
    }


    /**
     *
     * @return true if the base is destroyed.
     */
    public boolean isEndMatch() {
        endMatch = base.isEntityDestroyed();
        return endMatch;
    }

    public void clearUnits(){
        units.clear();
    }

    private void spawnAUnit(int tickId){
        if(unspawnedUnits.isEmpty()) return;
        Unit u = unspawnedUnits.pop();
        spawnedUnits.add(u);
        EventManager.getInstance().addEvent(new SpawnEvent(tickId,u.getId(),u.TYPE,getSpawnPoint(),ID));
    }


    @Override
    public String toString() {
        if (row == 0 || col == 0) return "";

        WarEntity[][] warEntities = new WarEntity[row][col];
        for (int i = 0; i < row; ++i)
            for (int j = 0; j < col; ++j)
                warEntities[i][j] = structures[i][j];
        for (Unit u : units) {
            warEntities[u.getPosition().y][u.getPosition().x] = u;
        }

        String toRet = "";

        for (int j = 0; j < warEntities[0].length; ++j) toRet += "   " + j;
        toRet += '\n';

        for (int i = 0; i < warEntities.length; ++i) {
            toRet += i;
            for (int j = 0; j < warEntities[0].length; ++j) {
                if (warEntities[i][j] != null) toRet += " " + warEntities[i][j].symbol();
                else toRet += "  - ";
            }
            toRet += '\n';
        }

        return toRet;
    }


    /***
     * Generates a Json containing the map information.
     * @return a string containing the current state
     */
    public String toJson() {
        JSONObject map = new JSONObject();
        map.put("id", ID);
        map.put("width", col);
        map.put("height", row);

        JSONObject baseJson = new JSONObject();
        baseJson.put("id", base.getId());
        baseJson.put("health", base.getHealthPoint());
        baseJson.put("position", positionToJson(base.getPosition()));

        map.put("base", baseJson);
        map.put("spawn", positionToJson(spawnPoint));

        JSONArray turrets = new JSONArray();

        for (Structure[] a : structures) {
            for (Structure b : a) {
                if (b instanceof Turret) {
                    Turret turret = (Turret) b;
                    JSONObject turretJson = new JSONObject();
                    turretJson.put("id", turret.getId());
                    turretJson.put("type", turret.TYPE.name());
                    turretJson.put("position", positionToJson(turret.getPosition()));
                    turretJson.put("destroyed", turret.isEntityDestroyed());

                    turrets.put(turretJson);
                }
            }
        }

        map.put("turrets", turrets);

        JSONArray units = new JSONArray();

        for (WarEntityType.UnitType type : WarEntityType.UnitType.values()) {

            int count = 0;
            for (Unit unit : this.units) {
                if (unit.TYPE == type) {
                    count++;
                }
            }

            if (count > 0) {
                JSONObject unitJson = new JSONObject();
                unitJson.put("type", type.name());
                unitJson.put("quantity", count);
                units.put(unitJson);
            }
        }

        map.put("units", units);

        return map.toString();
    }


    private JSONObject positionToJson(Point position) {

        JSONObject p = new JSONObject();
        p.put("x", position.x);
        p.put("y", position.y);

        return p;
    }
}
