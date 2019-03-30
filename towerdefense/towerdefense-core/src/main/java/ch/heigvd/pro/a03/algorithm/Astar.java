package ch.heigvd.pro.a03.algorithm;

import java.util.*;


/**
 * A* algorithm for path finding
 *
 * */

public class Astar {

    private static int DEFAULT_HV_COST = 10; // normal movement cost by default
    private static int DEFAULT_DIAGONAL_COST = 14; //diagonal cost by default --might not be used--
    private int horiVertiCost; //normal movement cost
    private int diagonalCost; //diagonal cost
    private Position[][] searchArea;
    private PriorityQueue<Position> openList;
    private Set<Position> closedSet;
    private Position initialPosition;
    private Position finalPosition;



    public Astar(int rows, int cols, Position initialPosition, Position finalPosition, int hvCost, int diagonalCost) {
        this.horiVertiCost=hvCost;
        this.diagonalCost = diagonalCost;
        this.initialPosition=initialPosition;
        this.finalPosition=finalPosition;
        this.searchArea = new Position[rows][cols];
        this.openList = new PriorityQueue<Position>(new Comparator<Position>() {
            @Override
            public int compare(Position p0, Position p1) {
                return Integer.compare(p0.getFinalCost(), p1.getFinalCost());
            }
        });
        setPosition();
        this.closedSet = new HashSet<>();
    }

    private void setPosition() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Position p = new Position(i, j);
                p.calculateHeuristic(finalPosition);
                this.searchArea[i][j] = p;
            }
        }
    }

    public Astar(int rows, int cols, Position initialPosition, Position finalPosition) {
        this(rows, cols, initialPosition, finalPosition, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
    }

    public void setBlocks(int[][] blocksArray) {//will add the blockage in the map
        for (int i = 0; i < blocksArray.length; i++) {
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            setBlock(row, col);
        }
    }


    public List<Position> findPath() {
        openList.add(initialPosition);
        while (!isEmpty(openList)) {
            Position current = openList.poll();
            closedSet.add(current);
            if (isFinalPosition(current)) {
                return getPath(current);
            } else {
                //addAdjacent(current); -need implementation of 4 movements
            }
        }
        return new ArrayList<Position>();
    }

    private List<Position> getPath(Position current) {
        List<Position> path = new ArrayList<Position>();
        path.add(current);
        Position parent;
        while ((parent = current.getParent()) != null) {
            path.add(0, parent);
            current = parent;
        }
        return path;
    }

    private void setBlock(int row, int col){
        this.searchArea[row][col].setBlock(true);
    }

    private boolean isEmpty(PriorityQueue<Position> openList) {
        return openList.size() == 0;
    }

    private boolean isFinalPosition(Position current) {
        return current.equals(finalPosition);
    }











}
