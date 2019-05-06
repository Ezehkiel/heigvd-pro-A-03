package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.warentities.Structure;

import java.awt.*;
import java.util.LinkedList;

public class Map {

    private Dimension size;
    private Structure[][] structures;
    //private Collection<Unit> units;

    public Map(int width, int height) {
        this.size = new Dimension(width, height);
        structures = new Structure[width][height];
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

    public boolean inMap(int x, int y) {
        return x >= 0 && x < size.width && y >= 0 && y < size.height;
    }
}
