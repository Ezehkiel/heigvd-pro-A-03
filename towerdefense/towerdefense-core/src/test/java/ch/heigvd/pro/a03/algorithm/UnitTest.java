package ch.heigvd.pro.a03.algorithm;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnitTest {

    @Test
    public void positionTest(){

        List<Point> path=new ArrayList<>();
        path.add(new Point(0,3));
        path.add(new Point(1,3));
        path.add(new Point(2,3));
        path.add(new Point(3,3));
        path.add(new Point(4,3));
        path.add(new Point(5,3));
        Iterator<Point> it=path.iterator();

        while(it.hasNext()){

            System.out.println(it.next());
        }



    }
}
