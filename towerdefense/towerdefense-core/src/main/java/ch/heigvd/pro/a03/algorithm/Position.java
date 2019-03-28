package ch.heigvd.pro.a03.algorithm;

public class Position {

    private int heuristic;
    private int cost;
    private int finalCost;
    private int row;
    private int col;
    private boolean isBlock;
    private Position parent;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void calculateHeuristic(Position finalPosition) {
        heuristic = Math.abs(finalPosition.row - row) + Math.abs(finalPosition.col - col);
    }

    public void setPositionData(Position current, int cost) {
        int tmpCost = current.cost + cost;
        parent=current;
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
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public String toString() {
        return "Position [row=" + row + ", col=" + col + "]";
    }

    public int getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(int finalCost) {
        this.finalCost = finalCost;
    }
}
