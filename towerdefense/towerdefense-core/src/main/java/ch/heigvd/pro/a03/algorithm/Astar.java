package ch.heigvd.pro.a03.algorithm;

import java.util.*;


/**
 * A* algorithm for path finding
 *
 * */

public class Astar {

    private static int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
    private static int DEFAULT_DIAGONAL_COST = 14;
    private int horiVertiCost; //normla movement cost
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













}
