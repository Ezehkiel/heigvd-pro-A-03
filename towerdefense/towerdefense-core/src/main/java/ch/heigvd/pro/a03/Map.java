package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.algorithm.Astar;
import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;
import java.util.LinkedList;

public class Map {

    private Dimension size;
    private Structure[][] structures;
    private LinkedList<Unit> units;
    private int row;
    private int col;
    private Base base;
    private boolean endSimulation;


    public Map(int row, int col, Base base) {
        this.row=row;
        this.col=col;
        this.size = new Dimension(row, col);
        structures = new Structure[row][col];
        units=new LinkedList<>();
        this.base=base;
        setStructureAt(base,base.getPosition().y,base.getPosition().x);

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

    public Structure getStructureAt(int row, int col) {

        if (!inMap(row,col)) {
            return null;
        }

        return structures[row][col];
    }

    public void setStructureAt(Structure structure, int row, int col) {

        if (!inMap(row, col)) {
            throw new IndexOutOfBoundsException("Position not in map");
        }

        structures[row][col] = structure;

    }

    public void addUnit(Unit u){
        units.add(u);
    }


    public Structure[][] getStructures() {
        return structures;
    }

    public void setUnits(LinkedList<Unit> units) {
        this.units = units;
    }

    public LinkedList<Unit> getUnits() {
        return units;
    }

    public boolean inMap(int row, int col) {
        return (row >= 0 && row < this.row && col >= 0 && col < this.col);
    }

    public void update() {

        for(int i =0; i<row;++i){
            for(int j=0;j<col;++j){
                if(structures[i][j] != null){
                    structures[i][j].update(this);
                }

            }

        }

        for(Unit u: units){

            u.update(this);
        }

    }


    public boolean isEndSimulation(){


        endSimulation = true;
        for(Unit u: units){
            if (!u.isEntityDestroyed()) {
                endSimulation = false;
                break;
            }
        }

        if(endSimulation){
            units.clear();
        }

        return endSimulation;

    }

    public boolean isEndMatch(){


        return base.isEntityDestroyed();
    }




}
