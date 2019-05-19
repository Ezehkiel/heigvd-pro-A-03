package ch.heigvd.pro.a03.algorithm;


/***
 *
 * Class able to represent a vector of 2 dimensions.
 * @author Nicodeme Stalder
 *
 */
public class Vec2 {

    private int row; //the y coordinate
    private int col; //the x coordinate

    /***
     *
     * @param row the y coordinate
     * @param col the x coordinate
     */
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


    @Override
    public String toString(){
        return "(" + row + ", " + col + ')';
    }

    /***
     *
     * @param v the vector to be compared to.
     * @return true if the two vectors got the same values.
     */
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
