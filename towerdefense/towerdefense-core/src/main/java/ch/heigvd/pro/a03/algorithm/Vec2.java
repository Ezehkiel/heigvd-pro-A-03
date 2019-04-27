package ch.heigvd.pro.a03.algorithm;

import java.awt.*;

public class Vec2 {

    private int row;
    private int col;

    public Vec2(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Point getCoord(){
        return new Point(row, col);
    }

    @Override
    public String toString(){
        return "(" + row + ", " + col + ')';
    }

    public boolean equals(Vec2 v){
        return row == v.getRow() && col == v.getCol();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof  Vec2){
            Vec2 v = (Vec2) o;
            return row == v.getRow() && col == v.getCol();
        }
        else return false;
    }
}
