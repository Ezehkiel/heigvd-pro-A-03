package ch.heigvd.pro.a03.algorithm;

import java.util.*;

/***
 *
 *         Position initialPosition = new Position(2, 1);
 *         Position finalPosition = new Position(2, 5);
 *         int rows = 6;
 *         int cols = 7;
 *         Astar aStar = new Astar(rows, cols, initialPosition, finalPosition);
 *         int[][] blocksArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
 *         aStar.setBlocks(blocksArray);
 *         List<Position> path = aStar.findPath();
 *         for (Position p : path) {
 *             System.out.println(node);
 *         }
 *
 *         //Search Area
 *         //      0   1   2   3   4   5   6
 *         // 0    -   -   -   -   -   -   -
 *         // 1    -   -   -   B   -   -   -
 *         // 2    -   I   -   B   -   F   -
 *         // 3    -   -   -   B   -   -   -
 *         // 4    -   -   -   -   -   -   -
 *         // 5    -   -   -   -   -   -   -
 *
 *         //Expected output
 *         //Node [row=2, col=1]
 *         //Node [row=2, col=2]
 *         //Node [row=1, col=2]
 *         //Node [row=0, col=2]
 *         //Node [row=0, col=3]
 *         //Node [row=0, col=4]
 *         //Node [row=1, col=4]
 *         //Node [row=2, col=4]
 *         //Node [row=2, col=5]
 *
 *         //Search Path 
 *         //      0   1   2   3   4   5   6
 *         // 0    -   -   *   *   *   -   -
 *         // 1    -   -   *   B   *   -   -
 *         // 2    -   I*  *   B   *  *F   -
 *         // 3    -   -   -   B   -   -   -
 *         // 4    -   -   -   -   -   -   -
 *         // 5    -   -   -   -   -   -   -
 *
 *
 *
 *
 */

/**
 * A* algorithm for path finding
 *
 * */

public class Astar {

    private static int DEFAULT_HV_COST = 10; // normal movement cost by default
    //private static int DEFAULT_DIAGONAL_COST = 14; //diagonal cost by default --might not be used--
    private int horiVertiCost; //normal movement cost
    //private int diagonalCost; //diagonal cost --might not be used, need to speek to the team--
    private Position[][] searchArea;
    private PriorityQueue<Position> openList;
    private Set<Position> closedSet;
    private Position initialPosition;
    private Position finalPosition;



    public Astar(int rows, int cols, Position initialPosition, Position finalPosition, int hvCost) {
        this.horiVertiCost=hvCost;
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
        this(rows, cols, initialPosition, finalPosition, DEFAULT_HV_COST);
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
                addAdjacent(current);
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

    private void addAdjacent(Position current) {
        addAdjacentUpperRow(current);
        addAdjacentMiddleRow(current);
        addAdjacentLowerRow(current);
    }

    private void addAdjacentLowerRow(Position current) {
        int row = current.getRow();
        int col = current.getCol();
        int lowerRow = row + 1; //the [0][0] is on the top left
        if (lowerRow < searchArea.length) {
            checkPosition(current, col, lowerRow, horiVertiCost);
        }
    }

    private void addAdjacentMiddleRow(Position current) {
        int row = current.getRow();
        int col = current.getCol();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkPosition(current, col - 1, middleRow, horiVertiCost);
        }
        if (col + 1 < searchArea[0].length) {
            checkPosition(current, col + 1, middleRow, horiVertiCost);
        }
    }

    private void addAdjacentUpperRow(Position current) {
        int row = current.getRow();
        int col = current.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkPosition(current, col, upperRow, horiVertiCost);
        }
    }

    private void checkPosition(Position current, int col, int row, int cost) {
        Position adjacentPosition = searchArea[row][col];
        if (!adjacentPosition.isBlock() && !closedSet.contains(adjacentPosition)) {
            if (!openList.contains(adjacentPosition)) {
                adjacentPosition.setPositionData(current, cost);
                openList.add(adjacentPosition);
            } else {
                boolean changed = adjacentPosition.checkBetterPath(current, cost);
                if (changed) {
                    // Remove and Add the changed position, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified position
                    openList.remove(adjacentPosition);
                    openList.add(adjacentPosition);
                }
            }
        }
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
