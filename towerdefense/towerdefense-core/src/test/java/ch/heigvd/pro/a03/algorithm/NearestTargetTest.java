package ch.heigvd.pro.a03.algorithm;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NearestTargetTest {

    @Test
    void simpleTest(){

        //The * represent the expected resulting path (from S to the nearer T)
        //(The path excludes S and T)
        //      0   1   2   3   4   5   6
        // 0    S   *   *   -   -   T   -
        // 1    -   -   *   _   -   -   -
        // 2    -   -   T   -   T   -   -
        // 3    -   -   -   -   -   -   -
        // 4    -   T   -   T   -   -   -
        // 5    -   -   -   -   -   -   -


        List<Vec2> targets = new LinkedList<>();
        targets.add(new Vec2(0,5));
        targets.add(new Vec2(2,2));
        targets.add(new Vec2(2,4));
        targets.add(new Vec2(4,1));
        targets.add(new Vec2(4,3));

        List<Vec2> expectedPath = new LinkedList<>();
        expectedPath.add(new Vec2(1,0));
        expectedPath.add(new Vec2(2,0));
        expectedPath.add(new Vec2(2,1));

        NearestTarget nt = new NearestTarget(6, 7, targets);

        //Note: there must be a more general way to test the path correctness...
        assertEquals(expectedPath, nt.pathToNearerTarget(new Vec2(0,0)));
    }

}
