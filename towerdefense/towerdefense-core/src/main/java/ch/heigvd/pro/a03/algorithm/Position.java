package ch.heigvd.pro.a03.algorithm;

/**
* Represents the position of a current object, Implemented in a Astar algorithm. Tracks the current cost of a position
* and compares it's old value in order to fin a "cheapest" position.
*/

public class Position {

    private int heuristic; //estimated value
    private int cost;//the cost of the current position
    private int finalCost;
    private int row;//coordinate y of the grid
    private int col;//coordinate x of the grid
    private boolean isBlock; //might be a rock/some kind of barrier/turret/etc..
    private Position parent; //in order to keep track of his older position


    /**
     * @brief Sets the position of the object at the grid.
     * @param row coordinate y of the grid
     * @param col coordinate x of the grid
     */
    public Position(int row, int col) {
        this.setRow(row);
        this.setCol(col);
    }


    /**
     * @brief Estimates the total cost of the trajectory.
     * @param finalPosition the target
     */
    public void calculateHeuristic(Position finalPosition) {
        heuristic = Math.abs(finalPosition.getRow() - getRow()) + Math.abs(finalPosition.getCol() - getCol());
    }


    /**
     * @brief Sets the new cost to the position
     * @param current current position
     * @param cost the movement cost
     */
    public void setPositionData(Position current, int cost) {
        int tmpCost = current.cost + cost;
        setParent(current);
        this.cost=tmpCost;
        calculateFinalCost();
    }


    /**
     * @brief check's for a better path (cheapest).
     * @param current the current position
     * @param cost the movement cost
     * @return false is there is not a better path
     */
    public boolean checkBetterPath(Position current, int cost) {
        int tmpCost = current.cost + cost;
        if (tmpCost < this.cost) {
            setPositionData(current, cost);
            return true;
        }
        return false;
    }


    /**
     * @brief calculates the final cost (considers the heuristic)
     */
    private void calculateFinalCost() {
        int finalCost = this.cost + this.heuristic;
        this.setFinalCost(finalCost);
    }

    @Override
    public boolean equals(Object arg0) {
        Position other = (Position) arg0;
        return this.getRow() == other.getRow() && this.getCol() == other.getCol();
    }

    @Override
    public String toString() {
        return "Position [row=" + getRow() + ", col=" + getCol() + "]";
    }

    public int getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(int finalCost) {
        this.finalCost = finalCost;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public Position getParent() {
        return parent;
    }

    public void setParent(Position parent) {
        this.parent = parent;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
