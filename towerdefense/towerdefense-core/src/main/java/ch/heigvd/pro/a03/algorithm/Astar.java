/**
 *
 * inspired form:
 * https://security-consulting.icu/blog/2012/01/a-star-java/
 * https://github.com/marcelo-s/A-Star-Java-Implementation
 * https://dzenanhamzic.com/2016/12/16/a-star-a-algorithm-implementation-in-java/
 * https://codereview.stackexchange.com/questions/100716/lets-path-find-a-star
 *
 * */

package ch.heigvd.pro.a03.algorithm;

import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * class that performs a A* algorithm for path finding
 * @author Andres Moreno
 */

public class Astar {

    private static int DEFAULT_HV_COST = 10; //  movement cost by default
    private int horiVertiCost;
    private Position[][] searchArea;
    private PriorityQueue<Position> openList;
    private Set<Position> closedSet;
    private Position initialPosition;
    private Position finalPosition;

    /**
     * Constructor with a default value of movement of 10.
     *
     * @param rows            number of rows of the gird
     * @param cols            number of columns of the gird
     * @param initialPosition initial position
     * @param finalPosition   target position
     *
     */
    public Astar(int rows, int cols, Position initialPosition, Position finalPosition) {
        this.horiVertiCost = DEFAULT_HV_COST;
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
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


    /**
     * Fills the search area with positions.
     */
    private void setPosition() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Position p = new Position(i, j);
                p.calculateHeuristic(finalPosition);
                this.searchArea[i][j] = p;
            }
        }
    }


    /**
     * Sets the blocks that will be avoided.
     * @param blocksArray array containing the blocked squares coordinates.
     *
     */
    public void setBlocks(int[][] blocksArray) {//will add the blockage in the map
        for (int i = 0; i < blocksArray.length; i++) {
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            setBlock(row, col);
        }
    }

    /***
     *
     * Blocks a single position.
     *
     * @param row  y coordinate of the blocked position
     * @param col  x coordinate of the blocked position
     *
     */
    public void setBlockPos(int row, int col) {//will add the blockage in the map
        setBlock(row, col);
    }

    /**
     *
     * Add's the blocked positions to the search area.
     * @param row coordinate y of the grid
     * @param col coordinate x of the grid
     *
     */
    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    /**
     *
     * Retrieves the path from the current position
     * @param current the current position
     * @return A list containing the path to the current position
     *
     */
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

    /***
     *
     * @param openList the priorityQueue containing the positions.
     * @return true if the PriorityQueue is empty
     */
    private boolean isEmpty(PriorityQueue<Position> openList) {
        return openList.size() == 0;
    }

    /***
     *
     * @param current the current position
     * @return true is the current position is the destination position
     */
    private boolean isFinalPosition(Position current) {
        return current.equals(finalPosition);
    }



    /**
     *
     * @return the path to the target or an empty arraylist if there is none.
     */
    public List<Point> findPath() {
        openList.add(initialPosition);
        while (!isEmpty(openList)) {
            Position current = openList.poll(); //retrieve remove the first element of the Queue
            closedSet.add(current);
            if (isFinalPosition(current)) {
                List<Position> positionPath = getPath(current);
                List<Point> path = new ArrayList<Point>();
                for (int i = 1; i < positionPath.size(); i++) {
                    path.add(new Point(positionPath.get(i).getCol(), positionPath.get(i).getRow()));
                }
                return path;

            } else {
                addAdjacent(current);
            }
        }

        System.out.println("Unable to find a path, all blocked -position impossible-");
        return new ArrayList<Point>();
    }


    /***
     * Add's all the adjacent positions, need to take into account that the [0][0] is on the top left
     * @param current the current position
     */
    private void addAdjacent(Position current) {
        addAdjacentUpperRow(current);
        addAdjacentMiddleRow(current);
        addAdjacentLowerRow(current);
    }

    /***
     * Add's the position located on top of the current position
     * @param current the current position
     */
    private void addAdjacentLowerRow(Position current) {
        int row = current.getRow();
        int col = current.getCol();
        int lowerRow = row + 1;
        if (lowerRow < searchArea.length) {
            checkPosition(current, col, lowerRow, horiVertiCost);
        }
    }

    /***
     * Add's the position located on the right and on the left of the current position
     * @param current the current position
     */
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

    /***
     *  Add's the position located down of the current position
     * @param current the current position
     */
    private void addAdjacentUpperRow(Position current) {
        int row = current.getRow();
        int col = current.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkPosition(current, col, upperRow, horiVertiCost);
        }
    }

    /***
     * Checks if the adjacent position is not blocked, already added in the PriorityQueue or if there is a better path.
     * then it will be added to the queue.
     *
     * @param current the current position
     * @param col     coordinate x of the grid
     * @param row     coordinate y of the grid
     * @param cost    the cost of the movement
     */
    private void checkPosition(Position current, int col, int row, int cost) {
        Position adjacentPosition = searchArea[row][col];
        if (!adjacentPosition.isBlock() && !closedSet.contains(adjacentPosition)) {
            if (!openList.contains(adjacentPosition)) {
                adjacentPosition.setPositionData(current, cost);
                openList.add(adjacentPosition);
            } else {
                boolean changed = adjacentPosition.checkBetterPath(current, cost);
                if (changed) {
                    openList.remove(adjacentPosition);
                    openList.add(adjacentPosition);
                }
            }
        }
    }





}
