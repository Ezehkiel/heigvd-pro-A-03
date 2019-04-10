package ch.heigvd.pro.a03.algorithm;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;


public class NearestTargetTest {

    @Test
    void NearestNeighborTest(){
        //S is the starting point
        //T are the targets
        //      0   1   2   3   4   5   6
        // 0    S   -   -   -   -   T   -
        // 1    -   -   -   -   -   -   -
        // 2    -   -   -   -   T   -   -
        // 3    -   -   -   -   -   -   -
        // 4    -   T   -   T   -   -   -
        // 5    -   -   -   -   -   -   -

        //The nearest target is in (4,1)
        //the shortest path to it should have length 4
        //(the path does not include S and T)

        List<Vec2> targets = new LinkedList<>();
        targets.add(new Vec2(0,5));
        targets.add(new Vec2(2,4));
        targets.add(new Vec2(4,1));
        targets.add(new Vec2(4,3));

        int rows = 6;
        int cols = 7;
        Vec2 startingPoint = new Vec2(0,0);
        Vec2 expectedTarget = new Vec2(4,1);

        NearestTarget nt = new NearestTarget(6, 7, targets);
        assert(nt.getNearestTarget(startingPoint).equals(expectedTarget));
    }

}
