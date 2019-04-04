package ch.heigvd.pro.a03.algorithm;

public class Position {

    private int heuristic;
    private int cost;
    private int finalCost;
    private int row;
    private int col;
    private boolean isBlock;//might be a rock or some kind of barrier
    private Position parent;

    public Position(int row, int col) {
        this.setRow(row);
        this.setCol(col);
    }

    public void calculateHeuristic(Position finalPosition) {
        heuristic = Math.abs(finalPosition.getRow() - getRow()) + Math.abs(finalPosition.getCol() - getCol());
    }

    public void setPositionData(Position current, int cost) {
        int tmpCost = current.cost + cost;
        setParent(current);
        this.cost=tmpCost;
        calculateFinalCost();
    }

    public boolean checkBetterPath(Position current, int cost) {
        int tmpCost = current.cost + cost;
        if (tmpCost < this.cost) {
            setPositionData(current, cost);
            return true;
        }
        return false;
    }


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
