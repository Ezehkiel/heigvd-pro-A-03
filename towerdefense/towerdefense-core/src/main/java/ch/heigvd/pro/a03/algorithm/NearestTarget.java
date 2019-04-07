package ch.heigvd.pro.a03.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
        isMarked = new boolean[rows][cols];
        for(Vec2 v: targets) isTarget[v.getRow()][v.getCol()] = true;
    }

    public List<Vec2> pathToNearerTarget(Vec2 startingPosition){
        Node root = new Node(startingPosition, null);
        LinkedList<Node> queue = new LinkedList<>(); queue.add(root);
        isMarked[root.position.getRow()][root.position.getCol()] = true;
        while(true){
            List<Node> neighbours = getNeighboursOf(queue.remove());
            Node target = getATargetIn(neighbours);
            if(target != null) return pathToNode(target);
            queue.addAll(neighbours);
        }
    }

    private List<Node> getNeighboursOf(Node n){
        List<Node> l = new LinkedList<>();
        int row = n.position.getRow();
        int col = n.position.getCol();

        if(row > 0 && !isMarked[row - 1][col]){
            l.add(new Node(new Vec2(row - 1, col), n));
            isMarked[row - 1][col] = true;
        }
        if(row < rows && !isMarked[row + 1][col]){
            l.add(new Node(new Vec2(row + 1, col), n));
            isMarked[row + 1][col] = true;
        }
        if(col > 0 && !isMarked[row][col - 1]){
            l.add(new Node(new Vec2(row, col - 1), n));
            isMarked[row][col - 1] = true;
        }
        if(col < cols && !isMarked[row][col + 1]){
            l.add(new Node(new Vec2(row, col + 1), n));
            isMarked[row][col + 1] = true;
        }

        return l;
    }

    private Node getATargetIn(List<Node> nodes){
        for(Node n: nodes) if(isTarget[n.position.getRow()][n.position.getCol()]) return n;
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
