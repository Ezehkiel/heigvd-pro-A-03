package ch.heigvd.pro.a03.algorithm;

public class Position {

    private int heuristic;
    private int cost;
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
       // calculateFinalCost();
    }

    /*public boolean checkBetterPath(Position current, int cost) {
        int gCost = current.cost + cost;
        if (gCost < this.cost) {
            setNodeData(currentNode, cost);
            return true;
        }
        return false;
    }

    private void calculateFinalCost() {
        int finalCost = getG() + getH();
        setF(finalCost);
    }

    @Override
    public boolean equals(Object arg0) {
        Position other = (Position) arg0;
        return this.getRow() == other.getRow() && this.getCol() == other.getCol();
    }

    @Override
    public String toString() {
        return "Node [row=" + row + ", col=" + col + "]";
    }*/

}
