package ch.heigvd.pro.a03.algorithm;

import java.util.LinkedList;
import java.util.List;

public class NearestTarget {

    private int rows;
    private int cols;
    private boolean[][] isTarget;
    private boolean[][] isMarked;

    public NearestTarget(int rows, int cols, List<Vec2> targets){
        assert(rows > 0 && cols > 0);
        assert(!targets.isEmpty());
        this.rows = rows;
        this.cols = cols;
        isTarget = new boolean[rows][cols];
        for(Vec2 v: targets) isTarget[v.getRow()][v.getCol()] = true;
    }

    /**
     * @param startingPosition
     * @param range
     * @return the location of the nearest neighbour in given range
     * if their is none, null is returned
     */
    public Vec2 getNearestTarget(Vec2 startingPosition, int range){
        isMarked = new boolean[rows][cols];
        //Vec2 root = startingPosition;
        LinkedList<Vec2> queue = new LinkedList<>(); queue.add(startingPosition); queue.add(null);
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
            if(target != null) return target;
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
        if(row < rows && !isMarked[row + 1][col]){
            l.add(new Vec2(row + 1, col));
            isMarked[row + 1][col] = true;
        }
        if(col > 0 && !isMarked[row][col - 1]){
            l.add(new Vec2(row, col - 1));
            isMarked[row][col - 1] = true;
        }
        if(col < cols && !isMarked[row][col + 1]){
            l.add(new Vec2(row, col + 1));
            isMarked[row][col + 1] = true;
        }

        return l;
    }

    private Vec2 getATargetIn(List<Vec2> ps){
        for(Vec2 p: ps) if(isTarget[p.getRow()][p.getCol()]) return p;
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

}
