package ch.heigvd.pro.a03.algorithm;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;
//import static org.junit.jupiter.api.Assertions.assertEquals;

public class NearestTargetTest {

    /*@Test
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


        assertPathCorrectness(nt.pathToNearerTarget(new Vec2(0,0)),
                new Vec2(0,0), new Vec2(2,2), 3);

    }*/

    @Test
    void test1(){

        //S is the starting point
        //T are the targets
        //      0   1   2   3   4   5   6
        // 0    S   -   -   -   -   T   -
        // 1    -   -   -   _   -   -   -
        // 2    -   -   T   -   T   -   -
        // 3    -   -   -   -   -   -   -
        // 4    -   T   -   T   -   -   -
        // 5    -   -   -   -   -   -   -

        //The nearest target is in (2,2)
        //the shortest path to it should have length 3
        //(the path does not include S and T)

        List<Vec2> targets = new LinkedList<>();
        targets.add(new Vec2(0,5));
        targets.add(new Vec2(2,2));
        targets.add(new Vec2(2,4));
        targets.add(new Vec2(4,1));
        targets.add(new Vec2(4,3));

        int rows = 6;
        int cols = 7;
        Vec2 startingPoint = new Vec2(0,0);
        Vec2 expectedEndingPoint = new Vec2(2,2);
        int expectedPathLength = 3;

        NearestTarget nt = new NearestTarget(6, 7, targets);
        assertPathCorrectness(nt.pathToNearerTarget(startingPoint), startingPoint,
                expectedEndingPoint, expectedPathLength);

    }

    @Test
    void test2(){

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
        Vec2 expectedEndingPoint = new Vec2(4,1);
        int expectedPathLength = 4;

        NearestTarget nt = new NearestTarget(6, 7, targets);
        assertPathCorrectness(nt.pathToNearerTarget(startingPoint), startingPoint,
                expectedEndingPoint, expectedPathLength);

    }

    private void assertPathCorrectness(List<Vec2> path, Vec2 startingPoint,
                               Vec2 endingPoint, int expectedPathLength){
        if(startingPoint.equals(endingPoint) || areNeighbours(startingPoint, endingPoint)){
            if(expectedPathLength != 0) throw new IllegalArgumentException("expectedPathLength should be zero");
            assert(path.isEmpty());
            return;
        }

        assert(path.size() == expectedPathLength);
        assert(!path.isEmpty());
        assert(areNeighbours(path.get(0), startingPoint));
        assert(areNeighbours(path.get(path.size() - 1), endingPoint));
        Iterator<Vec2> it = path.iterator();
        Vec2 tmp = it.next();
        Vec2 tmp2;
        while(it.hasNext()){
            tmp2 = it.next();
            assert(areNeighbours(tmp, tmp2));
            tmp = tmp2;
        }
    }

    private boolean areNeighbours(Vec2 p1, Vec2 p2){
        return abs(p1.getCol() - p2.getCol()) + abs(p1.getRow() - p2.getRow()) == 1;
    }

}
