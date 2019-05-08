package ch.heigvd.pro.a03.algorithm;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.warentities.Structure;
import ch.heigvd.pro.a03.warentities.units.Unit;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class NearestTarget {

    private boolean[][] isMarked;
    private Map map;

    public NearestTarget(Map map){
        isMarked = new boolean[map.getRow()][map.getCol()];
        this.map = map;
    }

    /**
     * @param unit
     * @return the location of the nearest Structure in given range of the given unit
     * if their is none, null is returned
     */
    public Structure getNearestTarget(Unit unit){

        Vec2 startingPosition = new Vec2(unit.getPosition().y, unit.getPosition().x);
        int range = (int) unit.getRange();

        //clean marked locations
        cleanIsMarked();

        //Vec2 root = startingPosition;
        LinkedList<Vec2> queue = new LinkedList<>();
        queue.add(startingPosition);
        queue.add(null);
        isMarked[startingPosition.getRow()][startingPosition.getCol()] = true;
        for(int i = 0; i < range;){
            Vec2 next = queue.remove();
            if(next == null){
                queue.add(null);
                next = queue.remove();
                ++i;
            }
            List<Vec2> neighbours = getNeighboursOf(next);
            Vec2 target = getATargetIn(neighbours);
            if(target != null) return map.getStructureAt(target.getRow(), target.getCol());
            queue.addAll(neighbours);
        }
        return null;
    }

    private List<Vec2> getNeighboursOf(Vec2 p){
        List<Vec2> l = new LinkedList<>();
        int row = p.getRow();
        int col = p.getCol();

        if(row > 0 && !isMarked[row - 1][col]){
            l.add(new Vec2(row - 1, col));
            isMarked[row - 1][col] = true;
        }
        if(row < map.getRow() && !isMarked[row + 1][col]){
            l.add(new Vec2(row + 1, col));
            isMarked[row + 1][col] = true;
        }
        if(col > 0 && !isMarked[row][col - 1]){
            l.add(new Vec2(row, col - 1));
            isMarked[row][col - 1] = true;
        }
        if(col < map.getCol() && !isMarked[row][col + 1]){
            l.add(new Vec2(row, col + 1));
            isMarked[row][col + 1] = true;
        }

        return l;
    }

    private Vec2 getATargetIn(List<Vec2> ps){
        for(Vec2 p: ps) if(map.getStructureAt(p.getRow(),p.getCol()) != null) return p;
        return null;
    }

    private List<Vec2> pathToNode(Node n){
        LinkedList<Vec2> l = new LinkedList<>();
        while((n = n.parent).parent != null) l.addFirst(n.position);
        return l;
    }

    private static class Node{
        public Vec2 position;
        public Node parent;

        public Node(Vec2 position, Node parent) {
            this.position = position;
            this.parent = parent;
        }
    }

    private void cleanIsMarked(){
        for(int i = 0; i < isMarked.length; ++i)
            for(int j = 0; j < isMarked[0].length; ++j)
                isMarked[i][j] = false;
    }

}
