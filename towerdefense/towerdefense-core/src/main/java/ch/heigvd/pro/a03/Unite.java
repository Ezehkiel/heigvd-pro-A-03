package ch.heigvd.pro.a03;

 abstract public class Unite extends WarEntity {
    private int attackPoints;
    private int range;
    private int speed;

    abstract public int[] move(int oldX , int oldY);
}
