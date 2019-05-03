package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;
import java.util.LinkedList;

public class Map {

    private Dimension size;
    private Structure[][] structures;
    private LinkedList<Unit> units;

    public Map(int row, int col) {
        this.size = new Dimension(row, col);
        structures = new Structure[row][col];
    }

    public Structure getStructureAt(int x, int y) {

        if (!inMap(x, y)) {
            return null;
        }

        return structures[x][y];
    }

    public void setStructureAt(Structure structure, int x, int y) {

        if (!inMap(x, y)) {
            throw new IndexOutOfBoundsException("Position not in map");
        }

        structures[x][y] = structure;
    }

    public void setUnits(LinkedList<Unit> units) {
        this.units = units;
    }

    public LinkedList<Unit> getUnits() {
        return units;
    }

    public boolean inMap(int x, int y) {
        return x >= 0 && x < size.width && y >= 0 && y < size.height;
    }

    public void update() {

        for(int i =0; i<structures.length;++i){
            for(int j=0;j<structures[i].length;++j){
                if(structures[i][j] != null){
                    structures[i][j].update();
                }

            }

        }

        for(Unit u: units){

            u.update();
        }

    }
}
